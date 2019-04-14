var pt;

document.addEventListener('DOMContentLoaded', function () {
    pt = document.getElementById('picture').createSVGPoint();
});

function getPoint(event) {
    var x = event.clientX;
    console.log(x);
    var y = event.clientY;
    console.log(y);
    var cursorpt = pt.matrixTransform(document.getElementById('picture').getScreenCTM().inverse());
    var r = parseFloat(document.getElementById('form:R_text').innerHTML);
    document.getElementById("form:hiddenX").value = (x + cursorpt.x - 125) * r / 100;
    document.getElementById("form:Y").value = -(y + cursorpt.y - 125) * r / 100;
    document.getElementById("form:areaClickBtn").click();
}

// $("Y").on("change", function() {
//     var val = parseFloat(this.value) || 1;
//     if (val > 5) {
//        this.value = 5;
//     } else if (val< -3) {
//         this.value = -3;
//     }
//
// });

function validateY() {
    var yField = document.getElementById("form.Y");
    if (parseFloat(yField.value) > 5 || parseFloat(yField.value) < -5) {
        yField.value = 0.0;
    }
}