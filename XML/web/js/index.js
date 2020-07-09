function valueSearch() {
    var value = document.getElementById("idBrand");
    var nameValue = value.options[value.selectedIndex].value;
    console.log(nameValue)
}
function onSubmit() {
    document.getElementById("formBrand").submit();
}