#pragma once

#ifndef RITDLL
#ifdef _RITDLL
#define RITDLL __declspec(dllexport)
#else
#define RITDLL __declspec(dllimport)
#endif
#endif

#include <Windows.h>
#include <mutex>
#include <cassert>
#include <iostream>

namespace RIT
{
	class RITDLL SingletonFinalizer
	{
	public:
		typedef void(*FinalizerFunc)();

		static void addFinalizer(FinalizerFunc func);
		static void finalize();
	};

	template <typename T>
	class RITDLL Singleton final
	{
	private:
		static std::once_flag initFlag;
		static T* instance;
		static void create()
		{
			instance = new T;
			SingletonFinalizer::addFinalizer(&Singleton<T>::destroy);
		}
		static void destroy()
		{
			delete instance;
			instance = nullptr;
		}
	public:
		static T& getInstance()
		{
			std::call_once(initFlag, create);
			assert(instance);
			return *instance;
		}
	};

	template <typename T> std::once_flag Singleton<T>::initFlag;
	template <typename T> T* Singleton<T>::instance = nullptr;
}