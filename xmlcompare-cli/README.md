XmlCompare
======================

----------------------
Introduction
----------------------
XmlCompare is a command line tool that allows you to compare 2 XML and dump the results in an Excel document.<br/>
It is based on [XMLUnit](http://www.xmlunit.org/) for the comparison
As it relies on Excel using [Apcahe POI](https://poi.apache.org/) it is a windows only script.

It produces an Excel report as below:
![alt text](https://raw.githubusercontent.com/kdefombelle/xmlcompare/master/doc/excelReport.png "Lorem Ipsum XML comparison")

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
