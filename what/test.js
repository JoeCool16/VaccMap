var map;
var chicago = {lat: 41.85, lng: -87.65};
// main method
function CenterControl(controlDiv, map) {

  // Set CSS for the control border.
  var controlUI = document.createElement('div');
  controlUI.style.backgroundColor = '#fff';
  controlUI.style.border = '2px solid #fff';
  controlUI.style.borderRadius = '3px';
  controlUI.style.boxShadow = '0 2px 6px rgba(0,0,0,.3)';
  controlUI.style.cursor = 'pointer';
  controlUI.style.marginBottom = '22px';
  controlUI.style.textAlign = 'center';
  controlUI.title = 'Click to recenter the map';
  controlDiv.appendChild(controlUI);

  // Set CSS for the control interior.
  var controlText = document.createElement('div');
  controlText.style.color = 'rgb(25,25,25)';
  controlText.style.fontFamily = 'Roboto,Arial,sans-serif';
  controlText.style.fontSize = '16px';
  controlText.style.lineHeight = '38px';
  controlText.style.paddingLeft = '5px';
  controlText.style.paddingRight = '5px';
  controlText.textContent = 'Vacc Data';
  controlUI.appendChild(controlText);

  // Setup the click event listeners: simply set the map to Chicago.
  controlUI.addEventListener('click', function() {
    map.setCenter(chicago);
  });

}

function initMap(centerMap, latitude, longitude) {
  var sanFrancisco = new google.maps.LatLng(37.774546, -122.433523);
  map = new google.maps.Map(document.getElementById("map"), {
    center: chicago,
    zoom: 12,
    
  });
  //DO NOT DELETE THE COMMENT UNDER!!!! REALLY! DO NOT! IT IS NOT A COMMENT!
  var heatmapData = /* PUT ARRAYLIST HERE <CODE INSERTION POINT>*/  [{location: new google.maps.LatLng(35.869999999999997, -84.67), weight: 39}, {location: new google.maps.LatLng(33.83, -101.84), weight: 1704}, {location: new google.maps.LatLng(45.73, -110.22), weight: 36}, ];
 
  var heatmap = new google.maps.visualization.HeatmapLayer({
  data: heatmapData
   });
   heatmap.setMap(map);
  
  var centerControlDiv = document.createElement('div');
  var centerControl = new CenterControl(centerControlDiv, map);

  centerControlDiv.index = 1;
  map.controls[google.maps.ControlPosition.TOP_CENTER].push(centerControlDiv);
}
