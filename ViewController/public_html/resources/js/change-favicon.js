/*!
 * Dynamically changing favicons with JavaScript
 * Works in all A-grade browsers except Safari and Internet Explorer
 */
 
    /**
     * Check browser type and in depends on it supporting, 
     * with js or with server listener (parent name - <servLisParId>, name - <servLisType>). 
     * @param paramMap = {'faviconPath':'<pathToFavicon>', 'servLisParId':'<servLisParId>', 'servLisType':'<servLisType>' }
     */
    function defineBrowserType( paramMap ) {
        // Opera 8.0+
        var isOpera = (!!window.opr && !!opr.addons) || !!window.opera || navigator.userAgent.indexOf(' OPR/') >= 0;      
        // Firefox 1.0+
        var isFirefox = typeof InstallTrigger !== 'undefined';
        // At least Safari 3+: "[object HTMLElementConstructor]"
        var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
        // Internet Explorer 6-11
        var isIE = /*@cc_on!@*/false || !!document.documentMode;
        // Edge 20+
        var isEdge = !isIE && !!window.StyleMedia;
        // Chrome 1+
        var isChrome = !!window.chrome && !!window.chrome.webstore;
        // Blink engine detection
        var isBlink = (isChrome || isOpera) && !!window.CSS;
        
        if(isOpera || isFirefox || isChrome || isBlink) {  
            changeFavicon( paramMap );                           
        } else if (isSafari || isIE || isEdge) { 
            var servLisParId = paramMap['servLisParId'];
            var servLisType = paramMap['servLisType'];
            var compon = AdfPage.PAGE.findComponentByAbsoluteId( servLisParId );   
            AdfCustomEvent.queue(compon, servLisType, {}, false);  
        }
    }
    
    /**
     * Set favion witch lay here: paramMap['faviconPath'] 
     * @param paramMap 
     */
    function changeFavicon( paramMap ) {
        var link = document.createElement('link');
        var oldLink = document.getElementById('dynamic-favicon');
        link.id = 'dynamic-favicon';
        link.rel = 'shortcut icon'; 
        var faviconPath = paramMap['faviconPath'];
        link.href = faviconPath;
        if (oldLink) {
            document.head.removeChild(oldLink);
        }
            document.head.appendChild(link);
    }

