# VDatePicker Addon

Date & Time Picker addon for Vaadin 14

## Building and running demo

- git clone repository
- mvn clean install jetty:run

To see the demo, navigate to http://localhost:8080/

## Release notes

- **Version 1.0.0** Supports Vaadin 14

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:

- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.


# Developer Guide

## Using the component

- Basic usage
```
		VDatePicker datepicker = new VDatePicker("dd-mmm-yyyy");
    	datepicker.setLabel("Date Picker");
```
- Using Locale
```    	
    	VDatePicker frdatepicker = new VDatePicker("dd-mmm-yyyy");
    	frdatepicker.setLabel("Date Picker");
    	frdatepicker.setLocale(Locale.FRENCH);
```
- Use as Time Picker
```   	
    	VDatePicker timepicker = new VDatePicker();
    	timepicker.setLabel("Time Picker");
    	timepicker.setIncludeDate(false);
    	timepicker.setIncludeTime(true);
```