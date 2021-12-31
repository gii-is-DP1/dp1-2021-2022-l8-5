<%@ attribute name="padding" required="true" rtexprvalue="true" 
 description="Padding for cards cells" %>
<%@ attribute name="xsize" required="true" rtexprvalue="true" 
 description="xSize of the card to show" %>
<%@ attribute name="ysize" required="true" rtexprvalue="true" 
description="ySize of the card to show" %>
<%@ attribute name="worker" required="true" rtexprvalue="true" type="org.springframework.dwarf.worker.Worker"
description="Card to be rendered" %>

<img id="workerImg-${worker.getId()}" src="${worker.image}" style="display:none">
<script>
	var canvas = document.getElementById("canvas");
	var ctx = canvas.getContext("2d");
	var image = document.getElementById("workerImg-${worker.getId()}");
	ctx.drawImage(image,${worker.getPositionXInPixels(xsize+padding)},${worker.getPositionYInPixels(ysize+padding)},${75},${75});
</script>