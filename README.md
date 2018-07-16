# Triangles

Java application that takes in user-inputted data and turns it into a triangle-based graphic. Created in under 24 hours for StarterHacks 2018. Winner of the Best Design Award.

## How to use Triangles

### Running the application

Download all files. Compile and run InputPanel.java.

### Using the application

In the starting window, input the name of a data point under the "Label" box and the value of that data point under the "Data" box. Use the +/- buttons on the bottom to add or remove data points as desired. There is a maximum of five data points allowed. Press submit to continue.

The next window will create a graphic made of triangles, with the colours of the triangles corresponding to each data point. The legend is located on the right.

Clicking the "close" button on the second window will close the second window only, allowing you to generate another graphic with the first window. Entering new data on the first window and pressing submit without closing the second window will generate the corresponding graphic in a new window and close the previously generated graphic simultaneously. Closing either window without using the built in "close" button will close the entire application.

### A few quirks

* Because this was created for a hackathon, not all desired features were successfully implemented. The "save" button on the second window only prompts the user for a title for the graphic. It does not do anything with this information. We did not have the time to create a database to store this and another panel to display previously saved graphics.

* If you close either window after pressing save and without closing the popup window, it will generate an error as the application closes. There are likely other scenarios as well in which closing the application will generate an error.

## Credits
### Created using

* JavaFX
* Java Swing 

### Creators

* [**Alice Cai**](https://github.com/alice-cai)
* [**Alisa Lin**](https://github.com/alisa-lin)
* [**Ordenica Wu**](https://github.com/Ordencia)

## Screenshots
![Input Panel](https://i.imgur.com/GGvj33o.png)
![Graphics Panel](https://i.imgur.com/VDlz289.png)

## Last comments

This project is not meant to be particularly useful. It was created for fun and should be used for fun. 
