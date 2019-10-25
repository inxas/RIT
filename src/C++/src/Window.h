#pragma once

#include "RIT.h"
#include <functional>

#ifdef __cplusplus
extern "C" {
#endif // __cplusplus

namespace RIT
{
	static constexpr LPCTSTR WindowClassName = TEXT("RIT");
	typedef std::function<void(UINT, WPARAM, LPARAM)> MessageCallback;

	class RITDLL Window;
	class RITDLL WindowManager
	{
	private:
		static LRESULT CALLBACK wndProc(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam);
	public:
		WindowManager();
		~WindowManager();
		static WindowManager& getInstance();
		Window* createWindow(HINSTANCE hInst, MessageCallback* callback);

		void destroyWindow(Window* window);
	};
	class RITDLL Window
	{
	private:
		HWND hWnd;
		Window(HINSTANCE hInst, MessageCallback* callback);
		~Window();
	public:
		friend WindowManager;

		void setAlwaysOnTop(bool alwaysOnTop);
		void setBounds(int x, int y, int w, int h);
		void setClientBounds(int x, int y, int w, int h);
		void setTitle(LPCTSTR title);
		void setVisible(bool visible);
		void toBack();
		void toFront();

		bool isAlwaysOnTop();
		void getBounds(RECT* rect);
		void getClientBounds(RECT* rect);
		LPTSTR getTitle();
		void freeTitle(LPTSTR title);
		bool isVisible();

		void nextMessage();

		void dispose();
	};

}

#ifdef __cplusplus
}
#endif // __cplusplus
