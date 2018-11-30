# PersianDateTimePicker
### Only works with anroidx projects And DataBinding With Material Components

#### built top on [PersianDate](https://github.com/samanzamani/PersianDate) and [shawnlin NumberPicker](https://github.com/ShawnLin013/NumberPicker)


## Requirment
* AndroidX
* DataBinding
* MaterialComponent Theme
* Minimum Android Version [15]()


# ScreanShots
<p align="center">
  <img src="https://github.com/hamed8080/PersianDateTimePicker/blob/master/ScreanShots/button.png" width="350" title="Button" alt="Button">
  <img src="https://github.com/hamed8080/PersianDateTimePicker/blob/master/ScreanShots/dialog.png" width="350" title="Dialog" alt="Dialog">
</p>

##  Getting Started
1- add line below on build.gradle project level
```
maven { url "https://jitpack.io" }
```
2- add this line to enable DataBinding
```
dataBinding {
   enabled = true
}
```

3- add line below on build.gradle on app level
```
implementation 'com.github.hamed8080:PersianDateTimePicker:1.01'
```

4- add this in your xml layout 
```
<ir.amozkade.hamed.datetimepickerlib.mvvm.view.DateTimeButton
        android:id="@+id/dt"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/redLight"
        app:typeFaceText="iran_sans.ttf"
        app:title="BirthDate" />
```
4- set Date And Time like below on your activity or ... (mBinding mean dataBinding layout class if dont use data Binding set direct using findViewById method)
```
mBinding.dt.setDateTime("1397/08/09","13:22");
```
5- app Theme must be parent with or Descendent 
```
<style name="AppTheme" parent="Theme.MaterialComponents">
```
# Atrributes to set in Xml Layout
#### hide Date Or Hide Time 
```
app:hideTime="true"
app:hideDate="true"
```
#### birthDate
```
app:isBirthDate="true"
```

#### name of fon file in asset
```
app:typeFaceText="Arial.ttf"
```

# Authors
* [Hamed Hosseini](https://github.com/hamed8080)
