#include "RIT.h"

BOOL APIENTRY DllMain(HANDLE hMod, DWORD dwReason, LPVOID lpReserved)
{
	switch (dwReason) {
	case DLL_PROCESS_ATTACH:
		//std::cout << "Attach RIT" << std::endl;
		break;
	case DLL_THREAD_ATTACH:
		//std::cout << "Attach RIT Thread" << std::endl;
		break;
	case DLL_THREAD_DETACH:
		//std::cout << "Detach RIT Thread" << std::endl;
		break;
	case DLL_PROCESS_DETACH:
		//std::cout << "Detach RIT" << std::endl;
		break;
	}

	return TRUE;
}

namespace RIT
{
	constexpr int maxFinalizersSize = 256;
	static int numFinalizersSize = 0;
	static SingletonFinalizer::FinalizerFunc finalizers[maxFinalizersSize];

	void SingletonFinalizer::addFinalizer(FinalizerFunc func)
	{
		assert(numFinalizersSize < maxFinalizersSize);
		finalizers[numFinalizersSize++] = func;
	}
	void SingletonFinalizer::finalize()
	{
		for (int i = numFinalizersSize - 1; i >= 0; --i)
		{
			(*finalizers[i])();
		}
		numFinalizersSize = 0;
	}
}