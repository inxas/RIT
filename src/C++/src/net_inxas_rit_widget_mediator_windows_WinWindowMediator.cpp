#include "net_inxas_rit_widget_mediator_windows_WinWindowMediator.h"

#include "Window.h"
#include "JavaInfo.h"

JNIEXPORT jlong JNICALL Java_net_inxas_rit_widget_mediator_windows_WinWindowMediator_create
(JNIEnv* env, jobject obj)
{
	jobject thiz = env->NewGlobalRef(obj);
	static jclass mediator = env->GetObjectClass(thiz);
	// button,mode,key,x,y
	static jmethodID processMouseClick = env->GetMethodID(mediator, "processMouseClick", "(IIIII)V");
	static jmethodID processWindow = env->GetMethodID(mediator, "processWindow", "(I)V");

	static jclass MouseClickEvent = env->FindClass("net/inxas/rit/widget/event/MouseClickEvent");
	env->ExceptionCheck();
	static jfieldID f_LEFT_BUTTON = env->GetStaticFieldID(MouseClickEvent, "LEFT_BUTTON", "I");
	static jfieldID f_RIGHT_BUTTON = env->GetStaticFieldID(MouseClickEvent, "RIGHT_BUTTON", "I");
	static jfieldID f_CENTER_BUTTON = env->GetStaticFieldID(MouseClickEvent, "CENTER_BUTTON", "I");
	static jfieldID f_X_BUTTON_1 = env->GetStaticFieldID(MouseClickEvent, "X_BUTTON_1", "I");
	static jfieldID f_X_BUTTON_2 = env->GetStaticFieldID(MouseClickEvent, "X_BUTTON_2", "I");
	static jfieldID f_UP = env->GetStaticFieldID(MouseClickEvent, "UP", "I");
	static jfieldID f_DOWN = env->GetStaticFieldID(MouseClickEvent, "DOWN", "I");
	static jfieldID f_CLICK = env->GetStaticFieldID(MouseClickEvent, "CLICK", "I");
	static jfieldID f_DOUBLE_CLICK = env->GetStaticFieldID(MouseClickEvent, "DOUBLE_CLICK", "I");
	static jfieldID f_VK_LEFT_BUTTON = env->GetStaticFieldID(MouseClickEvent, "VK_LEFT_BUTTON", "I");
	static jfieldID f_VK_RIGHT_BUTTON = env->GetStaticFieldID(MouseClickEvent, "VK_RIGHT_BUTTON", "I");
	static jfieldID f_VK_CENTER_BUTTON = env->GetStaticFieldID(MouseClickEvent, "VK_CENTER_BUTTON", "I");
	static jfieldID f_VK_X_BUTTON_1 = env->GetStaticFieldID(MouseClickEvent, "VK_X_BUTTON_1", "I");
	static jfieldID f_VK_X_BUTTON_2 = env->GetStaticFieldID(MouseClickEvent, "VK_X_BUTTON_2", "I");
	static jfieldID f_VK_SHIFT_KEY = env->GetStaticFieldID(MouseClickEvent, "VK_SHIFT_KEY", "I");
	static jfieldID f_VK_CONTROL_KEY = env->GetStaticFieldID(MouseClickEvent, "VK_CONTROL_KEY", "I");
	static jint LEFT_BUTTON = env->GetStaticIntField(MouseClickEvent, f_LEFT_BUTTON);
	static jint RIGHT_BUTTON = env->GetStaticIntField(MouseClickEvent, f_RIGHT_BUTTON);
	static jint CENTER_BUTTON = env->GetStaticIntField(MouseClickEvent, f_CENTER_BUTTON);
	static jint X_BUTTON_1 = env->GetStaticIntField(MouseClickEvent, f_X_BUTTON_1);
	static jint X_BUTTON_2 = env->GetStaticIntField(MouseClickEvent, f_X_BUTTON_2);
	static jint UP = env->GetStaticIntField(MouseClickEvent, f_UP);
	static jint DOWN = env->GetStaticIntField(MouseClickEvent, f_DOWN);
	static jint CLICK = env->GetStaticIntField(MouseClickEvent, f_CLICK);
	static jint D_CLICK = env->GetStaticIntField(MouseClickEvent, f_DOUBLE_CLICK);
	static jint VK_LEFT_BUTTON = env->GetStaticIntField(MouseClickEvent, f_VK_LEFT_BUTTON);
	static jint VK_RIGHT_BUTTON = env->GetStaticIntField(MouseClickEvent, f_VK_RIGHT_BUTTON);
	static jint VK_CENTER_BUTTON = env->GetStaticIntField(MouseClickEvent, f_VK_CENTER_BUTTON);
	static jint VK_X_BUTTON_1 = env->GetStaticIntField(MouseClickEvent, f_VK_X_BUTTON_1);
	static jint VK_X_BUTTON_2 = env->GetStaticIntField(MouseClickEvent, f_VK_X_BUTTON_2);
	static jint VK_SHIFT_KEY = env->GetStaticIntField(MouseClickEvent, f_VK_SHIFT_KEY);
	static jint VK_CONTROL_KEY = env->GetStaticIntField(MouseClickEvent, f_VK_CONTROL_KEY);
	env->ExceptionCheck();

	static jclass WindowEvent = env->FindClass("net/inxas/rit/widget/event/WindowEvent");
	env->ExceptionCheck();
	static jfieldID f_ACTIVATED = env->GetStaticFieldID(WindowEvent, "ACTIVATED", "I");
	static jfieldID f_DEACTIVATED = env->GetStaticFieldID(WindowEvent, "DEACTIVATED", "I");
	static jfieldID f_CLOSING = env->GetStaticFieldID(WindowEvent, "CLOSING", "I");
	static jfieldID f_CLOSED = env->GetStaticFieldID(WindowEvent, "CLOSED", "I");
	static jfieldID f_ICONIFIED = env->GetStaticFieldID(WindowEvent, "ICONIFIED", "I");
	static jfieldID f_MAXIMIZED = env->GetStaticFieldID(WindowEvent, "MAXIMIZED", "I");
	static jfieldID f_GAINED_FOCUS = env->GetStaticFieldID(WindowEvent, "GAINED_FOCUS", "I");
	static jfieldID f_LOST_FOCUS = env->GetStaticFieldID(WindowEvent, "LOST_FOCUS", "I");
	static jfieldID f_RESIZED = env->GetStaticFieldID(WindowEvent, "RESIZED", "I");
	static jfieldID f_RESTORED = env->GetStaticFieldID(WindowEvent, "RESTORED", "I");
	static jint ACTIVATED = env->GetStaticIntField(WindowEvent, f_ACTIVATED);
	static jint DEACTIVATED = env->GetStaticIntField(WindowEvent, f_DEACTIVATED);
	static jint CLOSING = env->GetStaticIntField(WindowEvent, f_CLOSING);
	static jint CLOSED = env->GetStaticIntField(WindowEvent, f_CLOSED);
	static jint ICONIFIED = env->GetStaticIntField(WindowEvent, f_ICONIFIED);
	static jint MAXIMIZED = env->GetStaticIntField(WindowEvent, f_MAXIMIZED);
	static jint GAINED_FOCUS = env->GetStaticIntField(WindowEvent, f_GAINED_FOCUS);
	static jint LOST_FOCUS = env->GetStaticIntField(WindowEvent, f_LOST_FOCUS);
	static jint RESIZED = env->GetStaticIntField(WindowEvent, f_RESIZED);
	static jint RESTORED = env->GetStaticIntField(WindowEvent, f_RESTORED);
	env->ExceptionCheck();

	auto callback = new RIT::MessageCallback([=](UINT msg, WPARAM wParam, LPARAM lParam) {
		JavaVM* vm = RIT::getVM();
		if (vm == NULL) return;
		JNIEnv* env;
		if (vm->GetEnv((void**)&env, JNI_VERSION_10) != JNI_OK) return;
		switch (msg) {
		case WM_LBUTTONDBLCLK:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, LEFT_BUTTON, D_CLICK, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_LBUTTONDOWN:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, LEFT_BUTTON, DOWN, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_LBUTTONUP:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, LEFT_BUTTON, UP, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_RBUTTONDBLCLK:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, RIGHT_BUTTON, D_CLICK, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_RBUTTONDOWN:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, RIGHT_BUTTON, DOWN, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_RBUTTONUP:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, RIGHT_BUTTON, UP, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_MBUTTONDBLCLK:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, CENTER_BUTTON, D_CLICK, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_MBUTTONDOWN:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, CENTER_BUTTON, DOWN, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_MBUTTONUP:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, CENTER_BUTTON, UP, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_XBUTTONDBLCLK:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, (HIWORD(wParam) & XBUTTON1) ? X_BUTTON_1 : X_BUTTON_2, D_CLICK, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_XBUTTONDOWN:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, (HIWORD(wParam) & XBUTTON1) ? X_BUTTON_1 : X_BUTTON_2, DOWN, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_XBUTTONUP:
		{
			jint vk = ((wParam & MK_LBUTTON) ? VK_LEFT_BUTTON : 0)
				| ((wParam & MK_RBUTTON) ? VK_RIGHT_BUTTON : 0)
				| ((wParam & MK_MBUTTON) ? VK_CENTER_BUTTON : 0)
				| ((wParam & MK_XBUTTON1) ? VK_X_BUTTON_1 : 0)
				| ((wParam & MK_XBUTTON2) ? VK_X_BUTTON_2 : 0)
				| ((wParam & MK_SHIFT) ? VK_SHIFT_KEY : 0)
				| ((wParam & MK_CONTROL) ? VK_CONTROL_KEY : 0);
			env->CallVoidMethod(thiz, processMouseClick, (HIWORD(wParam) & XBUTTON1) ? X_BUTTON_1 : X_BUTTON_2, UP, vk, LOWORD(lParam), HIWORD(lParam));
		}
		break;
		case WM_ACTIVATE:
		{
			env->CallVoidMethod(thiz, processWindow, LOWORD(wParam) != WA_INACTIVE ? ACTIVATED : DEACTIVATED);
		}
		break;
		case WM_CLOSE:
		{
			env->CallVoidMethod(thiz, processWindow, CLOSING);
		}
		break;
		case WM_DESTROY:
		{
			env->CallVoidMethod(thiz, processWindow, CLOSED);
		}
		break;
		case WM_SETFOCUS:
		{
			env->CallVoidMethod(thiz, processWindow, GAINED_FOCUS);
		}
		break;
		case WM_KILLFOCUS:
		{
			env->CallVoidMethod(thiz, processWindow, LOST_FOCUS);
		}
		break;
		case WM_SIZE:
		{
			if (wParam == SIZE_RESTORED) {
				env->CallVoidMethod(thiz, processWindow, RESIZED);
			}
			else if (wParam == SIZE_MINIMIZED) {
				env->CallVoidMethod(thiz, processWindow, ICONIFIED);
			}
			else if (wParam == SIZE_MAXIMIZED) {
				env->CallVoidMethod(thiz, processWindow, MAXIMIZED);
			}
		}
		break;
		case WM_QUERYOPEN:
		{
			env->CallVoidMethod(thiz, processWindow, RESTORED);
		}
		break;
		}
		if (env->ExceptionCheck()) {
			env->ExceptionDescribe();
		}
		return;
	});
	return (jlong)RIT::WindowManager::getInstance().createWindow(GetModuleHandle(TEXT("RIT")), callback);
}

JNIEXPORT void JNICALL Java_net_inxas_rit_widget_mediator_windows_WinWindowMediator_setAlwaysOnTop
(JNIEnv*, jobject, jlong wndPtr, jboolean visible)
{
	RIT::Window* window = (RIT::Window*)wndPtr;
	window->setAlwaysOnTop(visible);
}

JNIEXPORT void JNICALL Java_net_inxas_rit_widget_mediator_windows_WinWindowMediator_setBounds
(JNIEnv*, jobject, jlong wndPtr, jint x, jint y, jint width, jint height)
{
	RIT::Window* window = (RIT::Window*)wndPtr;
	window->setBounds(x, y, width, height);
}

JNIEXPORT void JNICALL Java_net_inxas_rit_widget_mediator_windows_WinWindowMediator_setClientBounds
(JNIEnv*, jobject, jlong wndPtr, jint x, jint y, jint width, jint height)
{
	RIT::Window* window = (RIT::Window*)wndPtr;
	window->setClientBounds(x, y, width, height);
}

JNIEXPORT void JNICALL Java_net_inxas_rit_widget_mediator_windows_WinWindowMediator_setTitle
(JNIEnv* env, jobject, jlong wndPtr, jstring title)
{
	RIT::Window* window = (RIT::Window*)wndPtr;

	const jchar* jtitle = env->GetStringChars(title, false);
	jsize len = env->GetStringLength(title);
	std::wstring wtitle;
	wtitle.assign(jtitle, jtitle + len);

	window->setTitle(wtitle.c_str());

	env->ReleaseStringChars(title, jtitle);
}

JNIEXPORT void JNICALL Java_net_inxas_rit_widget_mediator_windows_WinWindowMediator_setVisible
(JNIEnv*, jobject, jlong wndPtr, jboolean visible)
{
	RIT::Window* window = (RIT::Window*)wndPtr;
	window->setVisible(visible);
}

JNIEXPORT void JNICALL Java_net_inxas_rit_widget_mediator_windows_WinWindowMediator_toBack
(JNIEnv*, jobject, jlong wndPtr)
{
	RIT::Window* window = (RIT::Window*)wndPtr;
	window->toBack();
}

JNIEXPORT void JNICALL Java_net_inxas_rit_widget_mediator_windows_WinWindowMediator_toFront
(JNIEnv*, jobject, jlong wndPtr)
{
	RIT::Window* window = (RIT::Window*)wndPtr;
	window->toFront();
}

JNIEXPORT void JNICALL Java_net_inxas_rit_widget_mediator_windows_WinWindowMediator_nextMessage
(JNIEnv* env, jobject, jlong wndPtr)
{
	RIT::Window* window = (RIT::Window*)wndPtr;
	window->nextMessage();
}

JNIEXPORT void JNICALL Java_net_inxas_rit_widget_mediator_windows_WinWindowMediator_dispose
(JNIEnv*, jobject, jlong wndPtr)
{
	RIT::Window* window = (RIT::Window*)wndPtr;
	window->dispose();
}