#include "Window.h"

#include <iostream>

#ifdef __cplusplus
extern "C" {
#endif

	int main() {
		HMODULE rit = LoadLibrary(TEXT("RIT.dll"));
		std::cout << (rit ? "RIT was loaded." : "RIT cannot be loaded.") << std::endl;
		if (!rit) {
			return 1;
		}

		RIT::Window* window;
		volatile bool lock = true;

		std::thread th([&]() {
			RIT::MessageCallback callback = [&](UINT msg, WPARAM wParam, LPARAM lParam) {

			};
			window = RIT::WindowManager::getInstance().createWindow(rit, &callback);
			lock = false;
			while (true) {
				window->nextMessage();
			}
		});
		while (lock);

		window->setTitle(TEXT("RIT WINDOW TITLE"));
		window->setBounds(50, 50, 500, 500);
		window->setVisible(true);

		th.join();

		FreeLibrary(rit);
		return 0;
	}

#ifdef __cplusplus
}
#endif