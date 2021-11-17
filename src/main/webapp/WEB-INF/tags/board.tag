<%@ attribute name="board" required="false" rtexprvalue="true" type="org.springframework.dwarf.board.Board"
 description="Board to be rendered" %>
<canvas id="canvas" width="${board.width}" height="${board.height}"></canvas>
<img id="source" src="${board.background}" style="display:none">

<img id="1" src="resources/images/dwarf_card.png" style="display:none">
<img id="2" src="resources/images/dwarf_card.png" style="display:none">
<img id="3" src="resources/images/dwarf_card.png" style="display:none">
<img id="4" src="resources/images/dwarf_card.png" style="display:none">
<img id="5" src="resources/images/dwarf_card.png" style="display:none">
<img id="6" src="resources/images/dwarf_card.png" style="display:none">
<img id="7" src="resources/images/dwarf_card.png" style="display:none">
<img id="8" src="resources/images/dwarf_card.png" style="display:none">
<img id="9" src="resources/images/dwarf_card.png" style="display:none">
<script>
var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
var image = document.getElementById('source');
ctx.drawImage(image, 0, 0, ${board.width}, ${board.height});
</script>