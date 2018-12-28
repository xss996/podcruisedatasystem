$(document).ready(function () {
    $(".VerticalMenu>div>div:first-child").click(function () {
        $xz = $(".VerticalMenu>div>div:first-child");
        $($xz).not(this).children("i.fa-angle-right").css({ "transform": "rotate(0deg)", "color": "#000000" }).attr("leng", "")
        if ($(this).children("i.fa-angle-right").attr("leng") != "s") {
            $(this).children("i.fa-angle-right").attr("leng", "s")
            $(this).children("i.fa-angle-right").css({ "transform": "rotate(90deg)", "color": "#f9579e" })
        } else {
            $(this).children("i.fa-angle-right").attr("leng", "")
            $(this).children("i.fa-angle-right").css({ "transform": "rotate(0deg)", "color": "#000000" })
        }
        $($xz).not($(this)).siblings("[name='xz']").stop().slideUp(400)
        $(this).siblings("[name='xz']").slideToggle(400)

    })
    $VerticalMenu_scdj = null;
    $(".VerticalMenu>div>div:last-child>div").click(function () {
        $($VerticalMenu_scdj).css("background-color", "white");
        $(this).css("background-color","#00ff90");
        $VerticalMenu_scdj=$(this)
    })
})