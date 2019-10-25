CHCP 65001

cd /d %~dp0
cd Java\src

setlocal
set RITROOT=net\inxas\rit\
set OPTIONS=-h ..\..\C++\src -d ..\bin -encoding UTF8
set SRC=%RITROOT%*.java %RITROOT%widget\*.java %RITROOT%widget\mediator\*.java %RITROOT%widget\mediator\windows\*.java

javac %OPTIONS% %SRC%