function ext() {
    this.cfg.grid = {
        //background: 'white',
        shadow: false,
        //borderColor: 'green',
        //drawBorder: false,
        //drawGridlines: false

    };
this.cfg.legend = {
        show: false
        //location: 'n', // compass direction, nw, n, ne, e, se, s, sw, w.
        //placement: 'outsideGrid',
        //border: 'none',
        //marginTop: "10",
        //renderer: $.jqplot.EnhancedLegendRenderer,
        //show: true,
//        rendererOptions: {
//            numberRows: 1
//        }
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

