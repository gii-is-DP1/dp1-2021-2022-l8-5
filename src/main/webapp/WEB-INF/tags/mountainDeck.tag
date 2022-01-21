<%@ attribute name="padding" required="true" rtexprvalue="true" 
 description="Padding for cards cells" %>
<%@ attribute name="xsize" required="true" rtexprvalue="true" 
 description="xSize of the card to show" %>
<%@ attribute name="ysize" required="true" rtexprvalue="true" 
description="ySize of the card to show" %>
<%@ attribute name="mountainDeck" required="true" rtexprvalue="true" type="org.springframework.dwarf.mountainCard.MountainDeck"
description="Cards to be rendered" %>

<img id="cardImg2-${mountainDeck.getId()}" src="${mountainDeck.image}" style="display:none">
<script>
	var canvas = document.getElementById("canvas");
	var ctx = canvas.getContext("2d");
	var image = document.getElementById("cardImg2-${mountainDeck.getId()}");
	ctx.drawImage(image,${mountainDeck.getPositionXInPixels(xsize+padding)},${mountainDeck.getPositionYInPixels(ysize+padding)},${xsize},${ysize});
</script>