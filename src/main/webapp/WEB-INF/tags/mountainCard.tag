<%@ attribute name="padding" required="true" rtexprvalue="true" 
 description="Padding for cards cells" %>
<%@ attribute name="xsize" required="true" rtexprvalue="true" 
 description="xSize of the card to show" %>
 <%@ attribute name="ysize" required="true" rtexprvalue="true" 
 description="ySize of the card to show" %>
 <%@ attribute name="mountainCard" required="true" rtexprvalue="true" type="org.springframework.dwarf.mountain_card.MountainCard"
 description="Card to be rendered" %>
 <script>
 var canvas = document.getElementById("canvas");
 var ctx = canvas.getContext("2d");
 var image = document.getElementById('${mountainCard.id}');
 ctx.drawImage(image,${mountainCard.getPositionXInPixels(xsize+padding)},${mountainCard.getPositionYInPixels(ysize+padding)},${xsize},${ysize});
 </script>