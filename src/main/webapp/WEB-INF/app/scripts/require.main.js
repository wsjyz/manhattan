
if (!window.console) {
    window.console = {
        log:function (msg) {
        }
    };
}

var debug = false;
//html file src
var htmlSrc = '/templates/front/';
// 异步加载、后退支持（ie7及以下关闭history功能）
var historyEnable = !($.browser.msie && $.browser.version < 8);
// 页面主框架ID
var pageContentId = 'mainBody';


/*
 * RequireJS config
 */
requirejs.onError = function (err) {
    console.log(err.requireType);
    if (err.requireType === 'timeout') {
        console.log('modules: ' + err.requireModules);
    }
    throw err;
};


/**
 * 全局方法
 */

var T = {};

//登陆
T.login = function () {

}

T.dialog = function (conf) {
    require(['model.dialog'], function (obj) {
        obj.confirm(conf);
    })
}

T.uuid = function () {
    return new Date().getTime();
}

T.alert = function (conf) {
    require(['model.dialog'], function (d) {
        d.alert(conf);
    });
}


/*
 * 跳转到指定页
 */
T.jump = function (id, pageNo) {
    var jqId = "#" + id;
    var param = $(jqId).data('param');
    if (param) {
        var size = 10;
        var pageSize = $(jqId + " #pageSize").val();
        if (pageSize) {
            size = parseInt(pageSize);
        }
        var url;
        if (param.url.indexOf('?') != -1) {
            url = param.url + "&page.id=" + id + "&page.pageSize=" + size + "&page.pageNo="
                + pageNo;
        } else {
            url = param.url + "?page.id=" + id + "&page.pageSize=" + size + "&page.pageNo="
                + pageNo;
        }
        var queryParam = param.param || {};
        $.ajax({
            url:url,
            data:queryParam,
            type:"POST",
            async:false,
            context:$(jqId),
            dataType:'html',
            success:function (html) {
                $(this).html(html);
            }
        });
    }
};

T.editor = function (selector, conf) {
    if (selector.indexOf('#') == -1 && selector.indexOf('.') == -1)
        selector = '#' + selector;

    conf = conf || {upLinkUrl:"upload.php",
        upLinkExt:"zip,rar,txt",
        upImgUrl:"upload.php",
        upImgExt:"jpg,jpeg,gif,png",
        upFlashUrl:"upload.php",
        upFlashExt:"swf",
        upMediaUrl:"upload.php",
        upMediaExt:"avi"
    };
    require(['xheditor/xheditor-1.1.13-zh-cn.min'], function () {
        $(selector).xheditor(conf);
    });
};

// for normal form submit Handler
T.formSubmitHandler = function (obj) {
    try {
        var data = $(obj).serialize();
        formSubmit(data, obj);
    } catch (e) {
        T.dialog({title:'出错了', content:'保存时出错，请重试！'});
        if (debug)
            console.log('formSubmitHandler() 运行时出错。' + e);
    }
    return false;
}

T.isNotBlank = function (str) {
    str = $.trim(str);
    return str.length > 0;
}

/**
 * 初始化
 */
var INIT = (function ($) {
    var obj = {};

    // 主内容区元素ID
    obj.pageContentId = pageContentId;
    // 表单验证class
    obj.validateFormClass = 'form-validate';

    // ajax helper
    obj.ajaxHelper = function () {
        var loadingId = '__loading';
        var loadingEle = $('<div>').attr({'id':loadingId, 'style':'display:none;position:fixed;'
            + 'top:0;right:0;z-index:1;background-color:#DD4B39;padding:1px 8px;'})
            .html('loading...');
        $('body').append(loadingEle);

        $('#' + loadingId).ajaxStart(
            function () {
                $(this).show();
            }).ajaxStop(
            function () {
                $(this).hide();
            }).ajaxError(function (event, request, settings) {
                T.login();
                if (debug)
                    console.log('ajaxError >> ' + settings.url + '\t by ' + location.href);
            });

        $.ajaxSetup({
            error:function (jqXHR, textStatus, errorThrown) {
                try {
                    $('#' + loadingId).hide();
                } catch (e) {
                    //
                }
                switch (jqXHR.status) {
                    case 403:
                        T.login();
                        break;
                    case 404:
                        T.error();
                    default:
                        break;
                }
            }
        });

        // ie8以及以下禁用浏览器缓存
        if ($.browser.msie && $.browser.version <= 8) {
            $.ajaxSetup({
                cache:false
            });
        }
    };

    // history support
    obj.historySupport = function (historyEnable) {
        var linkEleObj = $('a[href]').filter(function (i, n) {
            return n.toString().indexOf('javascript:') == -1;
        });
        this._loadPage = function (id, url) {
            id = id || obj.pageContentId;
            $('#' + id).load(url, {_t:new Date().getTime()}, function (d) {
                // 初始化表单
                if (d.indexOf(obj.validateFormClass) != -1) {
                    INIT.formInit(id);
                }
            });
        };

        if (historyEnable) {
            require(['model.history'], function (history) {
                history.init(linkEleObj);
            })
        } else {
            $.each(linkEleObj, function (i, n) {
                $(n).click(function (e) {
                    obj._loadPage($(this).attr('data-eid'), n.href.toString());

                    return false;
                })
            })
        }
    };

    // 表单初始化
    obj.formInit = function (parentEleId) {
        parentEleId = (parentEleId == null || parentEleId == '') ? 'body' : '#' + parentEleId;
        var formObj = $(parentEleId + ' form');
        if (formObj.length == 0)
            return false;

        // 使用通用验证
        if (formObj.hasClass(obj.validateFormClass)) {
            require(['model.form.validate'], function (validate) {
                validate();
            });
        }

        // xheditor
        var xheditorEleSelector = '.xheditor, .xheditor-mfull, .xheditor-simple, .xheditor-mini, .xheditor-upload';
        var xheditorObj = formObj.find(xheditorEleSelector)
        if (xheditorObj.length > 0) {
            editor(xheditorEleSelector);
        }

        // 不使用通用验证类，封装基础异步提交
        var noValidateFormObj = formObj.not('.' + obj.validateFormClass).not('.normal');
        if (noValidateFormObj.length > 0) {
            $.each(noValidateFormObj, function (i, n) {
                $(n).attr('onsubmit', 'return T.formSubmitHandler(this);');
            })
        }

        // 页面首个form元素focus
        try {
            var firstEleObj = $('form').find("input, select, textarea").not(":submit, :reset, :image, [disabled]");
            firstEleObj[0].focus();
        } catch (e) {
            //
        }
    };

    obj.initAppId = function () {
        require(['initAppId' ], function (init) {
            initAppId(CONFIG.appId);
        });
    }
    return obj;
})(jQuery);

// 页面加载（A链接页面异步加载的JS代码实现，对应页面为实际页面时使用，产生浏览器“历史”记录）
T.loadPage = function (data, title, url) {
    var obj = INIT;
    if (historyEnable) {
        require(['model.history'], function (history) {
            history.loadPage(data, title, url);
        })
    } else {
        obj._loadPage(data.id, url);
    }
}

// DIV局部加载（不产生浏览器“历史”记录）
T.loadDiv = function (id, url) {
    INIT._loadPage(id, url);
}

T.WdatePicker = function (conf) {
    require(['My97DatePicker/WdatePicker'], function () {
        WdatePicker(conf);
    });
}

T.checkAll = function (parentObj, childName) {
    var checkFlag = $(parentObj).attr('checked');
    var flag = checkFlag == undefined ? false : true;
    $(':checkbox[name=' + childName + ']').each(function () {
        $(this).attr('checked', flag);
    });
}

T.tip = function (conf) {
    require(['model.alert'], function (a) {
        a.tip(conf);
    });
}

// ajax加载等待
INIT.ajaxHelper();
INIT.historySupport(historyEnable);
// 表单初始化
INIT.formInit(null);
INIT.initAppId();
