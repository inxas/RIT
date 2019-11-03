#include "Window.h"

#include <unordered_map>

namespace RIT
{
	LRESULT WindowManager::wndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam)
	{
		static std::unordered_map<HWND, Window*> windowMap;
		switch (msg) {
		case WM_PAINT:
			if(windowMap[hWnd]->paintBuf){
				COLORREF* ref = windowMap[hWnd]->paintBuf->colorRef;
				uint32_t width = windowMap[hWnd]->paintBuf->width;
				uint32_t len = windowMap[hWnd]->paintBuf->len;

				if (width <= 0 || len <= 0) break;

				PAINTSTRUCT ps;
				HDC hdc = BeginPaint(hWnd, &ps);

				long height = len / width;

				BITMAPINFO bmpInfo;
				bmpInfo.bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
				bmpInfo.bmiHeader.biBitCount = 32;
				bmpInfo.bmiHeader.biPlanes = 1;
				bmpInfo.bmiHeader.biWidth = width;
				bmpInfo.bmiHeader.biHeight = -height;
				bmpInfo.bmiHeader.biCompression = BI_RGB;

				LPDWORD pixel;
				HBITMAP hBmp = CreateDIBSection(hdc, &bmpInfo, DIB_RGB_COLORS, (void**)&pixel, NULL, 0);
				if (!hBmp) {
					EndPaint(hWnd, &ps);
					break;
				}

				HDC buf = CreateCompatibleDC(hdc);
				SelectObject(buf, hBmp);
				for (int i = 0; i < len; i++) {
					pixel[i] = ref[i];
				}

				BitBlt(hdc, 0, 0, width, height, buf, 0, 0, SRCCOPY);

				DeleteDC(buf);
				DeleteObject(hBmp);

				EndPaint(hWnd, &ps);
			}
			break;
		case WM_ERASEBKGND:
			return 1;
		case WM_CREATE:
			{
				CREATESTRUCT* lpcs = (CREATESTRUCT*)lParam;
				windowMap[hWnd] = (Window*)lpcs->lpCreateParams;
			}
			break;
		case WM_DESTROY:
			windowMap.erase(hWnd);
			break;
		}
		if (windowMap[hWnd]) {
			if (windowMap[hWnd]->callback) {
				(*windowMap[hWnd]->callback)(msg, wParam, lParam);
			}
		}
		if (msg == WM_CLOSE) return 0;
		return DefWindowProc(hWnd, msg, wParam, lParam);
	}
	WindowManager::WindowManager()
	{
	}
	WindowManager::~WindowManager()
	{
		
	}
	// static
	WindowManager& WindowManager::getInstance()
	{
		return RIT::Singleton<WindowManager>::getInstance();
	}

	Window* WindowManager::createWindow(HINSTANCE hInst, MessageCallback* callback)
	{
		static auto init = [=]() {
			WNDCLASS wc{
				CS_HREDRAW | CS_VREDRAW | CS_DBLCLKS,
				wndProc,
				0,0,
				hInst,
				LoadIcon(NULL , IDI_APPLICATION),
				LoadCursor(NULL , IDC_ARROW),
				(HBRUSH)GetStockObject(BLACK_BRUSH),
				NULL,
				WindowClassName
			};
			RegisterClass(&wc);
		};
		static std::once_flag flag;
		std::call_once(flag, init);
		return new Window(hInst, callback);
	}

	void WindowManager::destroyWindow(Window* window)
	{
		delete window;
	}

	Window::Window(HINSTANCE hInst, MessageCallback* callback) : callback(callback), paintBuf(nullptr)
	{
		hWnd = CreateWindow(
			WindowClassName, TEXT(""), WS_OVERLAPPEDWINDOW,
			0, 0, 0, 0,
			NULL, NULL,
			hInst, this);
	}
	Window::~Window()
	{
		std::cout << "delete window" << std::endl;
	}

	void Window::setAlwaysOnTop(bool alwaysOnTop)
	{
		SetWindowPos(hWnd, alwaysOnTop ? HWND_TOPMOST : HWND_NOTOPMOST, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
	}
	void Window::setBounds(int x, int y, int w, int h)
	{
		MoveWindow(hWnd, x, y, w, h, false);
	}
	void Window::setClientBounds(int x, int y, int w, int h)
	{
		RECT bounds, client;
		GetWindowRect(hWnd, &bounds);
		GetClientRect(hWnd, &client);

		int clientWidth = client.right - client.left;
		int clientHeight = client.bottom - client.top;

		POINT p = {};
		ClientToScreen(hWnd, &p);
		int left = p.x - bounds.left;
		int top = p.y - bounds.top;
		int right = bounds.right - p.x - clientWidth;
		int bottom = bounds.bottom - p.y - clientHeight;
		
		MoveWindow(hWnd, x - left, y - top, w + left + right, h + top + bottom, false);
	}
	void Window::setTitle(LPCTSTR title)
	{
		SetWindowText(hWnd, title);
	}
	void Window::setVisible(bool visible)
	{
		ShowWindow(hWnd, visible ? SW_SHOW : SW_HIDE);
	}
	void Window::toBack()
	{
		SetWindowPos(hWnd, HWND_BOTTOM, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
	}
	void Window::toFront()
	{
		SetWindowPos(hWnd, HWND_TOP, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
	}
	bool Window::isAlwaysOnTop()
	{
		return GetWindowLong(hWnd, GWL_EXSTYLE) & WS_EX_TOPMOST;
	}
	void Window::getBounds(RECT* rect)
	{
		GetWindowRect(hWnd, rect);
	}
	void Window::getClientBounds(RECT* rect)
	{
		POINT p = {};
		ClientToScreen(hWnd, &p);
		GetClientRect(hWnd, rect);
		rect->left += p.x;
		rect->right += p.x;
		rect->top += p.y;
		rect->bottom += p.y;
	}
	LPTSTR Window::getTitle()
	{
		int len = GetWindowTextLength(hWnd)+2;
		LPTSTR title = new TCHAR(len);
		GetWindowText(hWnd, title, len);
		return title;
	}
	size_t Window::getTitleLen()
	{
		return GetWindowTextLength(hWnd) + 2;
	}
	void Window::freeTitle(LPTSTR title)
	{
		delete title;
	}
	bool Window::isVisible()
	{
		return IsWindowVisible(hWnd);
	}
	void Window::nextMessage()
	{
		MSG msg;
		if (PeekMessage(&msg, hWnd, 0, 0, PM_REMOVE)) {
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}
	void Window::transfer(COLORREF* ref, uint32_t width, uint32_t len)
	{
		if (paintBuf) delete paintBuf;
		paintBuf = new PaintBuffer({ ref,width,len });
	}
	void Window::reflect()
	{
		InvalidateRect(hWnd, NULL, 0);
	}
	void Window::dispose()
	{
		DestroyWindow(hWnd);
	}
}