<%@ attribute name="padding" required="true" rtexprvalue="true" 
 description="Padding for cards cells" %>
<%@ attribute name="xsize" required="true" rtexprvalue="true" 
 description="xSize of the card to show" %>
<%@ attribute name="ysize" required="true" rtexprvalue="true" 
description="ySize of the card to show" %>
<%@ attribute name="specialDeck" required="true" rtexprvalue="true" type="org.springframework.dwarf.special_card.SpecialDeck"
description="Card to be rendered" %>

<img id="specialImg-${specialDeck.specialCard.get(0).getId()}" src="${specialDeck.specialCard.get(0).image}" style="display:none">

<script>
	var canvas = document.getElementById("canvas");
	var ctx = canvas.getContext("2d");
	var image = document.getElementById("specialImg-${specialDeck.specialCard.get(0).getId()}");
	ctx.drawImage(image,${specialDeck.getPositionXInPixels(xsize+padding)},${specialDeck.getPositionYInPixels(ysize+padding)},${xsize},${ysize});
</script>