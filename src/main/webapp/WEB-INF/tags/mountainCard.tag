<%@ attribute name="size" required="true" rtexprvalue="true" 
 description="Size of the card to show" %>
 <%@ attribute name="mountainCard" required="true" rtexprvalue="true" type="org.springframework.dwarf.mountain_card.MountainCard"
 description="Card to be rendered" %>
 <script>
 var canvas = document.getElementById("canvas");
 var ctx = canvas.getContext("2d");
 var image = document.getElementById('${mountainCard.id}');
 ctx.drawImage(image,${mountainCard.getPositionXInPixels(size)},${mountainCard.getPositionYInPixels(size)},${size},${size});
 </script>