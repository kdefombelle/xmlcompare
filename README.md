XmlCompare
======================

----------------------
Introduction
----------------------
XmlCompare is a command line tool that allows you to compare 2 XML and dump the results in an Excel document.<br/>
It is based on [XMLUnit](http://www.xmlunit.org/) for the comparison.<br/>
It relies on Excel using [Apache POI](https://poi.apache.org/).

It produces an Excel report as illustrated below:
![alt text](https://raw.githubusercontent.com/kdefombelle/xmlcompare/master/doc/excelReport.png "Lorem Ipsum XML comparison")

It proposes a GUI a script to execute this comparison.

----------------------
GUI
----------------------
This project is simple Java FX GUI to use this XmlCompare tool.
![alt text](https://raw.githubusercontent.com/kdefombelle/xmlcompare/master/doc/xmlcompareGui.png "XmlCompare GUI")

----------------------
Script
----------------------
The typical usage are illustrated below:

Standard
```sh
bin\xmlcompare.bat -c sample\control.xml -t sample\test.xml
```

Ignoring attributes comparison
```sh
bin\xmlcompare.bat -c sample\control.xml -t sample\test.xml -i
```

Setting a name to for your Excel report
```sh
bin\xmlcompare.bat -c sample\control.xml -t sample\test.xml -r myReport.xlsx
```

----------------------
Command Line Arguments
----------------------

|Option                 |Description|
|------                 |-----------|
|-c, --control <File>   |control file|
|-h, --help             |print this help message|
|-r, --report           |Excel report file name (default:report-${timestamp}.xlsx)|
|-t, --test <File>      |file to be compared|
