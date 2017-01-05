   function clacImgZoomParam( maxWidth, maxHeight, width, height ){
            var param = {top:0, left:0, width:width, height:height};
            console.log(param);
            if(maxWidth){
              rateWidth = width / maxWidth;
              rateHeight = height / maxHeight;
              if( rateWidth > rateHeight )
              {
                  param.width =  maxWidth;
                  param.height = Math.round(height / rateWidth);
                  rate=rateWidth;
              }else
              {
                  param.width = Math.round(width / rateHeight);
                  param.height = maxHeight;
                  rate=rateHeight;
              }
              /*param.left = Math.round((maxWidth - param.width) / 2);
              param.top = Math.round((maxHeight - param.height) / 2);*/
            }  
            console.log(rate);
            return param;
        }

    function change(file){
    // Get a reference to the fileList
    var files = !!file.files ? file.files : [];
    // If no files were selected, or no FileReader support, return
    if (!files.length || !window.FileReader) return;

        // Create a new instance of the FileReader
        var reader = new FileReader();
  
        // Read the local file as a DataURL
        reader.readAsDataURL(files[0]);
  
        // When loaded, set image data as background of div
		reader.onloadend = function(){
        var img=$('#photo');
        img.attr("src",this.result);
        $("#view_photo").attr("src",this.result);
        img.load(function(){
          // 加载完成 
          var img=$('#photo');
          img.width('auto');
          img.height('auto'); 
          
          var rect = clacImgZoomParam(300, 300, img.width(), img.height());
          img.width(rect.width);
          img.height(rect.height); 
          $("#preview").width(img.width()/3);
          $("#preview").height(img.width()/3*selectrate);
          init();
        });
      }
}	


    selectrate=1;
    rate=1;
			function preview(img, selection) {
				if (!selection.width || !selection.height)
					return;
        var img=$("#view_photo");
				var scaleX =  $("#preview").width() / selection.width;
				var scaleY =  $("#preview").height() / selection.height;

				$('#preview img').css({
					width : Math.round(scaleX *  $("#photo").width()),
					height : Math.round(scaleY * $("#photo").height()),
					marginLeft : -Math.round(scaleX * selection.x1),
					marginTop : -Math.round(scaleY * selection.y1)
				});
				
				$("#startX").val(Math.round(selection.x1*rate));
				$("#startY").val(Math.round(selection.y1*rate));
				$("#width").val(Math.round(selection.width*rate));
				$("#height").val(Math.round(selection.height*rate));
			}
			
			$(function() {
				init();
			});
      function init(){
        var width=$('#photo').width();
        var height=$('#photo').height();
        $('#photo').imgAreaSelect({
          aspectRatio : "1:1",
          handles : true,
          fadeSpeed : 200,
          onSelectChange : preview,
          //x1: 0, y1: 0, x2: 240, y2: 240
        });
      }