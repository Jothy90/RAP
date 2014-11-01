<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 10/24/14
  Time: 3:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap-slider.css">
    <link rel="stylesheet" type="text/css" href="resources/css/bootstrap/bootstrap-clockpicker.css">
    <link rel="stylesheet" type="text/css" href="resources/css/toggle-switch.css">
    <%--<link rel="stylesheet" type="text/css" href="resources/css/dia.css">--%>



    <%--Google pie chart for weather meter--%>
    <script type='text/javascript' src='https://www.google.com/jsapi'></script>

    <script type='text/javascript'>

        function reloadPage(){
            location.reload();
        }

        function request(){
            var url,xhr;
            url="requestForData?id=<c:out value="${device.id}"/>";

            xhr = new XMLHttpRequest();
            xhr.open('GET',url, true);
            xhr.send();
            xhr.onreadystatechange=function(){

                if (xhr.readyState==4)
                {
                    //alert(xhr.responseText);
                    var str = xhr.responseText;
                    var res = str.split(" ");
                    if(res[0].localeCompare('ERROR')==0){
                        alert("Some thing went Wrong in Device plz Refresh page after some time");
                    }else if(res[0].localeCompare('WORK')==0){
                        alert("WORK");
                        /*reloadPage();*/
                    }
                    else{
                        alert("Some thing went Wrong");
                    }
                }
            }
        }

    </script>
    <script type='text/javascript'>
        google.load('visualization', '1', {packages: ['gauge']});
        google.setOnLoadCallback(drawChart);
        function drawChart() {
            var data = google.visualization.arrayToDataTable([
                ['Label', 'Value'],
                ['\'C',<c:out value="${device.temperature}"/>]

            ]);

            var options = {
                min: 0, max: 50,
                width: 500, height: 165,
                greenFrom: 20, greenTo: 35,
                redFrom: 35, redTo: 50,
                yellowFrom: 0, yellowTo: 20,
                minorTicks: 3
            };

            var chart = new google.visualization.Gauge(document.getElementById('temp_meter'));

            chart.draw(data, options);
            var data1 = google.visualization.arrayToDataTable([
                ['Label', 'Value'],
                ['%',<c:out value="${device.humidity}"/>]

            ]);

            var options1 = {
                min: 0, max: 100,
                width: 500, height: 165,
                redFrom: 90, redTo: 100,
                yellowFrom: 45, yellowTo: 90,
                greenFrom: 0, greenTo: 45,
                minorTicks: 5
            };


            var chart1 = new google.visualization.Gauge(document.getElementById('hum_meter'));
            chart1.draw(data1, options1);
        }
    </script>

    <style type='text/css'>
        /* Example 1 custom styles */
        .slider.slider-horizontal {
            width: 500px;
            height: 20px;
        }
        .tooltip {
            margin-left: -14px;
        }
        /* .slider-track {
             width: 250%;
         }*/
        .well {
            min-height: 10px;
            padding: 10px;
            margin-bottom: 10px;
            width: 550px;
            background-color: #E0E0E0;
        }

        #ex1Slider .slider-selection {
            background: #BABABA;
        }
    </style>

    <title></title>
</head>

<body style="margin-top:60px" onload="load()">



<!-- Fixed navbar -->
<div id="dic_bubble" class="selection_bubble fontSize13 noSelect"
     style="z-index: 9999; border: 1px solid rgb(74, 174, 222); visibility: hidden;"></div>
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">RAP</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="#">Home</a></li>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Johnnirajh<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="j_spring_security_logout">Sign Out</a></li>
                        <li class="divider"></li>
                        <li><a href="#">About RAP</a></li>
                    </ul>
                </li>
            </ul>
        </div>

    </div>
</div>
<!-- /Fixed navbar -->

<div class="container">



    <!-- /container -->
    <a href="index.html"></a>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->


    <div class="row">


        <div class="thumbnail col-sm-6">

            <div class="row">
                <div class="lead col-sm-8 col-sm-offset-3">
                    <div class="row">
                        <div class="lead col-sm-11 col-sm-offset-1"> Temperature Readings</div>
                    </div>
                </div>
            </div>

            <div class="row">

                <div class="col-sm-4 col-sm-offset-1">
                    <br/> <br/> <br/>
                    <div  id='temp_meter'></div>
                </div>


                <div class="thumbnail col-sm-6">
                    <img src="resources/images/img4.png">
                </div>
            </div>

            <div class="lead col-sm-6" style="margin-left: 20px;">Temperature Range</div>
            <div class="well">
                <input id="sltl" data-slider-id='ex1Slider' type="text" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="<c:out value="${device.lowTemp}"/>"/>

            </div>
            <div class="well">
                <input id="slth" data-slider-id='ex1Slider' type="text" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="<c:out value="${device.highTemp}"/>"/>

            </div>
            <br/>



        </div>

        <div class="thumbnail col-sm-6">

            <div class="row">
                <div class="lead col-sm-8 col-sm-offset-3">
                    <div class="row">

                        <div class="lead col-sm-11 col-sm-offset-1">  Humidity Readings</div>
                     </div>
                </div>
            </div>

            <div class="row">

                <div class="col-sm-4 col-sm-offset-1">
                    <br/> <br/> <br/>
                    <div  id='hum_meter'></div>
                </div>


                <div class="thumbnail col-sm-6">
                    <img src="resources/images/img4.png">
                </div>
            </div>

            <div class="lead col-sm-6" style="margin-left: 20px;">Humidity Range</div>

            <div class="well">
                <input id="slhl" data-slider-id='ex1Slider' type="text" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="<c:out value="${device.lowHum}"/>"/>

            </div>
            <div class="well">
                <input id="slhh" data-slider-id='ex1Slider' type="text" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="<c:out value="${device.highHum}"/>"/>

            </div>
            <br/>

        </div>
    </div>
    <input type="button" onclick="request()" name="request" value="request">
</div>
<script type="text/javascript" src="resources/js/bootstrap/jquery-1.11.js"></script>
<script type="text/javascript" src="resources/js/bootstrap/bootstrap.min.js"></script>
<script  type="text/javascript" src="resources/js/jquery-1.11.0.min.js"></script>
<script  type="text/javascript" src="resources/js/bootstrap/bootstrap-slider.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap/bootstrap-clockpicker.min.js"></script>


<script  type="text/javascript">
    $(document).ready(function() {
        $("#sltl").slider({
            tooltip: 'always'
        });
        $("#slth").slider({
            tooltip: 'always'
        });
        $("#slhl").slider({
            tooltip: 'always'
        });
        $("#slhh").slider({
            tooltip: 'always'
        });
    });

</script>
</body>
</html>