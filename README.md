Clojurescript Reagent Components
============================


This project requires Java 8 and lein. Please download, install and verify that these tools are available before running the project. 

----------


## Overview
This project demonstrates building reusable Clojurescript UI components using Reagent. Multiple options for data flow between components is demonstrated. For teaching purposes, devcards are used to illustrate the display and UX of each component. 


### <i class="icon-cog">First time initialization

 1. Clone the project
	 ``` git clone https://github.com/esumitra/bosclj-reagent-lt ```
 2. Check java version
	 ``` java -version```
 3. Check lein version
	 ``` lein -v```
### <i class="icon-play">Run project
To start the development environment for the project,
```
lein figwheel esumitra
```

The devcards UI is now available at http://localhost:3449/index.html

### <i class="icon-refresh"> Modifying the UI
The development environment automatically detects changes to code and injects the updated, compiled code into the browser providing a UI REPL for fast interactive development.

Just edit the source file and save to see changes in the UI. Compilation errors are also reported in the UI and browser console log.
