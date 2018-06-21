function chartExtender() {
    this.cfg.grid = {
        background: 'white',
        shadow: false,
        //borderColor: 'green',
        drawBorder: false,
        drawGridlines: false

    };

    this.cfg.canvasOverlay = {
        show: true,
        showTootip: true,
        objects: [{verticalLine: {
                    "shadow": "false",
                    "lineWidth": 5,
                    "color": "rgb(255,0,0)",
                    "x": 50
                }}]};



    this.cfg.cursor = {
        style: 'crosshair', // A CSS spec for the cursor type to change the
        // cursor to when over plot.
        show: true,
        showTooltip: false, // show a tooltip showing cursor position.
        followMouse: true, // wether tooltip should follow the mouse or be stationary.
        tooltipLocation: 'se', // location of the tooltip either relative to the mouse
        // (followMouse=true) or relative to the plot.  One of
        // the compass directions, n, ne, e, se, etc.
        tooltipOffset: 6, // pixel offset of the tooltip from the mouse or the axes.
        showTooltipGridPosition: false, // show the grid pixel coordinates of the mouse
        // in the tooltip.
        showTooltipUnitPosition: false, // show the coordinates in data units of the mouse
        // in the tooltip.
        showTooltipDataPosition: true,
        //showCursorLegend: true,
        intersectionThreshold: 2,
        //cursorLegendFormatString:  '%s x:%s, y:%s',
        tooltipFormatString: '<table class="jqplot-highlighter"> \
                <tr><td colspan="2">%s</td></tr> \
                <tr><td>%s</td><td>%s</td></tr> \
            </table>', // sprintf style format string for tooltip values.

        useAxesFormatters: true, // wether to use the same formatter and formatStrings
        // as used by the axes, or to use the formatString
        showVerticalLine: true,
        showHorizontalLine: true, // specified on the cursor with sprintf.
        tooltipAxesGroups: [['axis', 'yAxis']] // show only specified axes groups in tooltip.  Would specify like:
                // [['xaxis', 'yaxis'], ['xaxis', 'y2axis']].  By default, all axes
                // combinations with for the series in the plot are shown.

    },
    this.cfg.highlighter = {
        show: true,
        tooltipContentEditor: tooltipContentFunction,
        showMarker: true,
        //lineWidthAdjust: 2.5, // pixels to add to the size line stroking the data point marker
        // when showing highlight.  Only affects non filled data point markers.
        //sizeAdjust: 5, // pixels to add to the size of filled markers when drawing highlight.
        showTooltip: true, // show a tooltip with data point values.
        tooltipLocation: 'sw', // location of tooltip: n, ne, e, se, s, sw, w, nw.
        fadeTooltip: false, // use fade effect to show/hide tooltip.
        tooltipFadeSpeed: "fast", // slow, def, fast, or a number of milliseconds.
        tooltipOffset: 2, // pixel offset of tooltip from the highlight.
        tooltipAxes: 'both', // which axis values to display in the tooltip, x, y or both.
        tooltipSeparator: ', ', // separator between values in the tooltip.
        useAxesFormatters: true, // use the same format string and formatters as used in the axes to
        // display values in the tooltip.
        tooltipFormatString: '%.5P', // sprintf format string for the tooltip.  only used if
        // useAxesFormatters is false.  Will use sprintf formatter with
        // this string, not the axes formatters.
        yvalues: 3
//                formatString:'<table class="jqplot-highlighter">\
//        <tr><td>Date:</td><td>%s</td></tr>\
//        <tr><td>Share Price:</td><td>%s</td></tr>\
//        <tr><td>close:</td><td>%s</td></tr></table>'


    };


    function tooltipContentFunction(str, seriesIndex, pointIndex, plot) {
        if (plot.series.length === 2) {
            var dateString = plot.series[seriesIndex].data[pointIndex][0];
            var dateString2 = new Date(dateString).toDateString();
            var shareLabel = plot.series[0].label;
            var shareString = plot.series[0].data[pointIndex][1]  ;

            var volumeString = plot.series[1].data[pointIndex][1];
            var styleString = "<p style='background: white;color:black;font-weight: normal;font-size:130%; margin:0px; border: 1px solid;border-radius: 8px;'>";
            var seriesArray;
            if (plot.series.length === 2)
                seriesArray = "dog";

            return styleString + "<br/>" + dateString2 + "<br/>" +"&nbsp;&nbsp;"+ shareLabel + ": " + shareString  + "&nbsp;&nbsp;<br/> Volume: " + volumeString + "<br/><br/></p>";
        } else {
            var dateString = plot.series[seriesIndex].data[pointIndex][0];
            var dateString2 = new Date(dateString).toDateString();
            var primaryShareString = plot.series[0].data[pointIndex][1];
            var primaryLabel = plot.series[0].label;
            var comparisonString = plot.series[1].data[pointIndex][1];
            var comparisonLabel = plot.series[1].label;
            var volumeString = plot.series[2].data[pointIndex][1];

            styleString = "<p style='background: white;color:black;font-weight: normal;font-size:130%; margin:0px; border: 1px solid;border-radius: 8px;'>";

            var seriesArray;
            if (plot.series.length === 2)
                seriesArray = "dog";

            return styleString + "<br/>" + dateString2 + "<br/>" +"&nbsp;&nbsp;" +primaryLabel + ": " + primaryShareString + "&nbsp;&nbsp;<br/> Volume: " + volumeString + "<br/>" + comparisonLabel + ": " + comparisonString + "<br/><br/>" + "</p>";
        }
    }
    ;

    this.cfg.legend = {
        show: true,
        location: 'n', // compass direction, nw, n, ne, e, se, s, sw, w.
        placement: 'outsideGrid',
        border: 'none',
        marginTop: "10",
        renderer: $.jqplot.EnhancedLegendRenderer,
        //show: true,
        rendererOptions: {
            numberRows: 1
        }
        //xoffset: 12,        // pixel offset of the legend box from the x (or x2) axis.
        //yoffset: 12       // pixel offset of the legend box from the y (or y2) axis.
    },
    this.cfg.seriesDefaults = {
        shadow: false,
        lineWidth: "2",
        //fillAndStroke: true,
        //fillColor: 'green',
        marginBottom: "10px",
        renderer: "$.jqplot.BarRenderer",
        rendererOptions: {
            barWidth: "2"
        }
    };

    this.cfg.seriesColors = ['#33ccff', '#061a00', '#33ccff', '#C7754C', '#17BDB8'];






}


