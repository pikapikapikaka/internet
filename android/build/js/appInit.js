/**
 * Created by zhaopeng on 2017/6/7.
 */
// Init App
var myApp = new Framework7({
    modalTitle: 'Framework7',
    // Enable Material theme
    material: true
});

// Expose Internal DOM library
var $$ = Dom7;

// Add main view
var mainView = myApp.addView('.view-main', {
});

// Show/hide preloader for remote ajax loaded pages
// Probably should be removed on a production/local app
$$(document).on('ajaxStart', function (e) {
    if (e.detail.xhr.requestUrl.indexOf('autocomplete-languages.json') >= 0) {
        // Don't show preloader for autocomplete demo requests
        return;
    }
    myApp.showIndicator();
});
$$(document).on('ajaxComplete', function (e) {
    if (e.detail.xhr.requestUrl.indexOf('autocomplete-languages.json') >= 0) {
        // Don't show preloader for autocomplete demo requests
        return;
    }
    myApp.hideIndicator();
});

/* ===== Change statusbar bg when panel opened/closed ===== */
$$('.panel-left').on('open', function () {
    $$('.statusbar-overlay').addClass('with-panel-left');
});
$$('.panel-right').on('open', function () {
    $$('.statusbar-overlay').addClass('with-panel-right');
});
$$('.panel-left, .panel-right').on('close', function () {
    $$('.statusbar-overlay').removeClass('with-panel-left with-panel-right');
});

/* ===== Generate Content Dynamically ===== */
var dynamicPageIndex = 0;
function createContentPage() {
    mainView.router.loadContent(
        '  <!-- Page, data-page contains page name-->' +
        '  <div data-page="dynamic-content" class="page">' +
        '    <!-- Top Navbar-->' +
        '    <div class="navbar">' +
        '      <div class="navbar-inner">' +
        '        <div class="left"><a href="#" class="back link icon-only"><i class="icon icon-back"></i></a></div>' +
        '        <div class="center">Dynamic Page ' + (++dynamicPageIndex) + '</div>' +
        '      </div>' +
        '    </div>' +
        '    <!-- Scrollable page content-->' +
        '    <div class="page-content">' +
        '      <div class="content-block">' +
        '        <p>Here is a dynamic page created on ' + new Date() + ' !</p>' +
        '        <p>Go <a href="#" class="back">back</a> or generate <a href="#" class="ks-generate-page">one more page</a>.</p>' +
        '      </div>' +
        '    </div>' +
        '  </div>'
    );
    return;
}
$$(document).on('click', '.ks-generate-page', createContentPage);

/**
 * 个人中心
 * @author zhaopeng
 **/
myApp.onPageInit('person', function (page) {
    $$('.nickname-prompt').on('click', function () {
        myApp.modal({
            title:  '昵称',
            text: '',
            afterText: '<div class="input-field"><input type="text" style="text-align: center;" class="modal-text-input"></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".nickname-prompt").find(".item-after").html($$(".modal-text-input").val());
                    }
                }
            ]
        });
        $$(".modal-text-input").val($$(".nickname-prompt").find(".item-after").html());
        $$(".modal-text-input").focus();
    });
    $$('.sex-prompt').on('click', function () {
        myApp.modal({
            title:  '性别',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="男" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">男</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="女"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">女</div></div>' +
            '</label></li></ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".sex-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.birthday-prompt').on('click', function () {
        myApp.modal({
            top:0,
            title:  '生日',
            text: '',
            afterText: '<div style="margin:0" class="list-block"><input type="text" style="text-align: center;" placeholder="生日" readonly="readonly" id="ks-picker-date"/></div><div id="ks-picker-date-container"></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".birthday-prompt").find(".item-after").html($$("#ks-picker-date").val());
                    }
                }
            ]
        });
        var today = new Date();
        var pickerInline = myApp.picker({
            input: '#ks-picker-date',
            container: '#ks-picker-date-container',
            toolbar: false,
            rotateEffect: true,
            value: [today.getFullYear(), today.getMonth(), today.getDate()],
            onChange: function (picker, values, displayValues) {
                var daysInMonth = new Date(picker.value[2], picker.value[0]*1 + 1, 0).getDate();
                if (values[1] > daysInMonth) {
                    picker.cols[1].setValue(daysInMonth);
                }
            },
            formatValue: function (p, values, displayValues) {
                return values[0] + '-' + values[1] + '-' + displayValues[2];
            },
            cols: [
                // Years
                {
                    values: (function () {
                        var arr = [];
                        for (var i = 1950; i <= 2030; i++) { arr.push(i); }
                        return arr;
                    })()
                },
                // Months
                {
                    values: ('1 2 3 4 5 6 7 8 9 10 11 12').split(' '),
                    displayValues: ('January February March April May June July August September October November December').split(' '),
                    textAlign: 'left'
                },
                // Days
                {
                    values: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],
                }
            ]
        });
    });
    $$('.address-prompt').on('click', function () {
        myApp.modal({
            title:  '所在地',
            text: '',
            afterText: '<div style="margin:0" class="list-block"><input type="text" style="text-align: center;" placeholder="所在地" readonly="readonly" id="ks-picker-dependent"/></div><div id="ks-picker-dependent-container"></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".address-prompt").find(".item-after").html($$("#ks-picker-dependent").val());
                    }
                }
            ]
        });
        // Dependent values
        var carVendors = {
            河北省: ['秦皇岛市', '石家庄市', '邢台市', '邯郸市', '唐山市'],
            北京: ['北京市'],
            天津: ['天津市'],
            山西省: ['太原市','大同市', '朔州市', '阳泉市', '长治市', '晋城市', '忻州市', '晋中市', '临汾市', '运城市', '吕梁市']
        };
        var pickerDependent = myApp.picker({
            input: '#ks-picker-dependent',
            container: '#ks-picker-dependent-container',
            toolbar: false,
            rotateEffect: true,
            formatValue: function (picker, displayValues, values) {
                return values[0] + ' ' + values[1];
            },
            cols: [
                {
                    textAlign: 'left',
                    values: ['河北省', '北京', '天津', '山西省'],
                    onChange: function (picker, country) {
                        if(picker.cols[1].replaceValues){
                            picker.cols[1].replaceValues(carVendors[country]);
                        }
                    }
                },
                {
                    values: carVendors.河北省,
                    width: 160
                }
            ]
        });
    });
    $$('.realname-prompt').on('click', function () {
        myApp.modal({
            title:  '真实姓名',
            text: '',
            afterText: '<div class="input-field"><input type="text" style="text-align: center;" class="modal-text-input"></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".realname-prompt").find(".item-after").html($$(".modal-text-input").val());
                    }
                }
            ]
        });
        $$(".modal-text-input").val($$(".realname-prompt").find(".item-after").html());
        $$(".modal-text-input").focus();
    });
    $$('.phone-prompt').on('click', function () {
        myApp.modal({
            title:  '手机号',
            text: '',
            afterText: '<div class="input-field"><input type="text" style="text-align: center;" class="modal-text-input"></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".phone-prompt").find(".item-after").html($$(".modal-text-input").val());
                    }
                }
            ]
        });
        $$(".modal-text-input").val($$(".phone-prompt").find(".item-after").html());
        $$(".modal-text-input").focus();
    });
    $$('.email-prompt').on('click', function () {
        myApp.modal({
            title:  '邮箱',
            text: '',
            afterText: '<div class="input-field"><input type="text" style="text-align: center;" class="modal-text-input"></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".email-prompt").find(".item-after").html($$(".modal-text-input").val());
                    }
                }
            ]
        });
        $$(".modal-text-input").val($$(".email-prompt").find(".item-after").html());
        $$(".modal-text-input").focus();
    });
    $$('.school-prompt').on('click', function () {
        myApp.modal({
            title:  '学校',
            text: '',
            afterText: '<div class="input-field"><input type="text" style="text-align: center;" class="modal-text-input"></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".school-prompt").find(".item-after").html($$(".modal-text-input").val());
                    }
                }
            ]
        });
        $$(".modal-text-input").val($$(".school-prompt").find(".item-after").html());
        $$(".modal-text-input").focus();
    });
    $$('.department-prompt').on('click', function () {
        myApp.modal({
            title:  '院系',
            text: '',
            afterText: '<div class="input-field"><input type="text" style="text-align: center;" class="modal-text-input"></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".department-prompt").find(".item-after").html($$(".modal-text-input").val());
                    }
                }
            ]
        });
        $$(".modal-text-input").val($$(".department-prompt").find(".item-after").html());
        $$(".modal-text-input").focus();
    });
    $$('.position-prompt').on('click', function () {
        myApp.modal({
            title:  '职位',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="教授" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">教授</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="讲师"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">讲师</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="院长"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">院长</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="系主任"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">系主任</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="校长"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">校长</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="其他"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">其他</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".position-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.positiontime-prompt').on('click', function () {
        myApp.modal({
            title:  '就职时间',
            text: '',
            afterText: '<div style="margin:0" class="list-block"><input type="text" style="text-align: center;" placeholder="就职时间" readonly="readonly" id="ks-picker-date"/></div><div id="ks-picker-date-container"></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".positiontime-prompt").find(".item-after").html($$("#ks-picker-date").val());
                    }
                }
            ]
        });
        var today = new Date();
        var pickerInline = myApp.picker({
            input: '#ks-picker-date',
            container: '#ks-picker-date-container',
            toolbar: false,
            rotateEffect: true,
            value: [today.getFullYear(), today.getMonth(), today.getDate()],
            onChange: function (picker, values, displayValues) {
                var daysInMonth = new Date(picker.value[2], picker.value[0]*1 + 1, 0).getDate();
                if (values[1] > daysInMonth) {
                    picker.cols[1].setValue(daysInMonth);
                }
            },
            formatValue: function (p, values, displayValues) {
                return values[0] + '-' + values[1] + '-' + displayValues[2];
            },
            cols: [
                // Years
                {
                    values: (function () {
                        var arr = [];
                        for (var i = 1950; i <= 2030; i++) { arr.push(i); }
                        return arr;
                    })()
                },
                // Months
                {
                    values: ('1 2 3 4 5 6 7 8 9 10 11 12').split(' '),
                    displayValues: ('January February March April May June July August September October November December').split(' '),
                    textAlign: 'left'
                },
                // Days
                {
                    values: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31],
                }
            ]
        });
    });
    $$('.logout').on('click', function () {
        myApp.closeModal('.popover');
        myApp.modal({
            title:  '您确定要退出登录吗？',
            text: '',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick: function () {
                        myApp.modal({
                            title:  '已退出登录！',
                            text: '',
                            buttons: [
                                {
                                    text: '确定',
                                    bold: true,
                                    onClick: function () {
                                        mainView.router.loadPage("login.html");
                                    }
                                }
                            ]
                        });
                    }
                }
            ]
        });
    });
});
/**
 * 设置
 * @author zhaopeng
 **/
myApp.onPageInit('config', function (page) {
    $$('.language-prompt').on('click', function () {
        myApp.modal({
            title: '语言',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="跟随系统" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">跟随系统</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="English"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">English</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="简体中文"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">简体中文</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".language-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.font-prompt').on('click', function () {
        myApp.modal({
            title: '字体大小',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="极小"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">极小</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="小"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">小</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="标准" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">标准</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="大"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">大</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="极大"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">极大</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".font-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.begin-prompt').on('click', function () {
        myApp.modal({
            title: '一周开始于',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="区域默认规则" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">区域默认规则</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="周日"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">周日</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="周六"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">周六</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="周一"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">周一</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".begin-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.remind-prompt').on('click', function () {
        myApp.modal({
            title: '提醒',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="无"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">无</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="语音播报" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">语音播报</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="振动"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">振动</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="响铃"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">响铃</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".remind-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.defaultremind-prompt').on('click', function () {
        myApp.modal({
            title: '默认提醒时间',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="无"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">无</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="开始时"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">开始时</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="10分钟前" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">10分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="30分钟前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">30分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="1小时前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">1小时前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="自定义"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">自定义</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".defaultremind-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
});
/**
 * 搜索日程
 * @author zhaopeng
 **/
myApp.onPageInit('search', function (page) { });

/**
 * 月视图
 * @author zhaopeng
 **/
myApp.onPageInit('month', function (page) {
    var monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August' , 'September' , 'October', 'November', 'December'];
    var calendarInline = myApp.calendar({
        container: '#ks-calendar-inline-container',
        value: [new Date()],
        weekHeader: false,
        header: false,
        footer: false,
        toolbarTemplate:
        '<div class="toolbar calendar-custom-toolbar">' +
        '<div class="toolbar-inner">' +
        '<div class="left">' +
        '<a href="#" class="link icon-only"><i class="icon icon-back"></i></a>' +
        '</div>' +
        '<div class="center"></div>' +
        '<div class="right">' +
        '<a href="#" class="link icon-only"><i class="icon icon-forward"></i></a>' +
        '</div>' +
        '</div>' +
        '</div>',
        onOpen: function (p) {
            $$('.calendar-custom-toolbar .center').text(monthNames[p.currentMonth] +', ' + p.currentYear);
            $$('.calendar-custom-toolbar .left .link').on('click', function () {
                calendarInline.prevMonth();
            });
            $$('.calendar-custom-toolbar .right .link').on('click', function () {
                calendarInline.nextMonth();
            });
        },
        onMonthYearChangeStart: function (p) {
            $$('.calendar-custom-toolbar .center').text(monthNames[p.currentMonth] +', ' + p.currentYear);
        }
    });
    $$('.addSchedule').on('click', function () {
        $$('.speed-dial').removeClass("speed-dial-opened");
    });
}).trigger();
/**
 * 周视图
 * @author zhaopeng
 **/
myApp.onPageInit('week', function (page) {
    $$('.addSchedule').on('click', function () {
        $$('.speed-dial').removeClass("speed-dial-opened");
    });
}).trigger();
/**
 * 日视图
 * @author zhaopeng
 **/
myApp.onPageInit('day', function (page) {
    $$('.addSchedule').on('click', function () {
        $$('.speed-dial').removeClass("speed-dial-opened");
    });
}).trigger();

/**
 * 选择日程类型
 * @author zhaopeng
 **/
myApp.onPageInit('type', function (page) { });

/**
 * 选择课程方式
 * @author zhaopeng
 **/
myApp.onPageInit('coursetype', function (page) { });

/**
 * 添加会议
 * @author zhaopeng
 **/
myApp.onPageInit('addMeeting', function (page) {
    $$('.meetingRepeat-prompt').on('click', function () {
        myApp.modal({
            title: '重复',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="一次性活动" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">一次性活动</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="每天"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">每天</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="每周"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">每周</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="每月"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">每月</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="自定义"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">自定义</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".meetingRepeat-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.meetingRemind-prompt').on('click', function () {
        myApp.modal({
            title: '提醒',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="无"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">无</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="开始时"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">开始时</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="10分钟前" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">10分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="30分钟前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">30分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="1小时前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">1小时前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="自定义"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">自定义</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".meetingRemind-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
});

/**
 * 开始时间
 * @author zhaopeng
 **/
myApp.onPageInit('startTime', function (page) {
    var today = new Date();
    var pickerInline = myApp.picker({
        input: '#ks-picker-day',
        container: '#ks-picker-day-container',
        toolbar: false,
        rotateEffect: true,
        value: [today.getFullYear(), today.getMonth(), today.getDate()],
        onChange: function (picker, values, displayValues) {
            var daysInMonth = new Date(picker.value[2], picker.value[0] * 1 + 1, 0).getDate();
            if (values[1] > daysInMonth) {
                picker.cols[1].setValue(daysInMonth);
            }
        },
        formatValue: function (p, values, displayValues) {
            return values[0] + '-' + values[1] + '-' + displayValues[2];
        },
        cols: [
            // Years
            {
                values: (function () {
                    var arr = [];
                    for (var i = 1950; i <= 2030; i++) {
                        arr.push(i);
                    }
                    return arr;
                })()
            },
            // Months
            {
                values: ('1 2 3 4 5 6 7 8 9 10 11 12').split(' '),
                displayValues: ('January February March April May June July August September October November December').split(' '),
                textAlign: 'left'
            },
            // Days
            {
                values: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31],
            }
        ]
    });
    var pickerInline = myApp.picker({
        input: '#ks-picker-time',
        container: '#ks-picker-time-container',
        toolbar: false,
        rotateEffect: true,
        value: [today.getHours(), (today.getMinutes() < 10 ? '0' + today.getMinutes() : today.getMinutes())],
        onChange: function (picker, values) {
            var daysInMonth = new Date(picker.value[2], picker.value[0] * 1 + 1, 0).getDate();
            if (values[1] > daysInMonth) {
                picker.cols[1].setValue(daysInMonth);
            }
        },
        formatValue: function (p, values) {
            return values[0] + ':' + values[1];
        },
        cols: [
            // Hours
            {
                values: (function () {
                    var arr = [];
                    for (var i = 0; i <= 23; i++) { arr.push(i); }
                    return arr;
                })()
            },
            // Divider
            {
                divider: true,
                content: ':'
            },
            // Minutes
            {
                values: (function () {
                    var arr = [];
                    for (var i = 0; i <= 59; i++) { arr.push(i < 10 ? '0' + i : i); }
                    return arr;
                })()
            }
        ]
    });
});

/**
 * 结束时间
 * @author zhaopeng
 **/
myApp.onPageInit('endTime', function (page) {
    var today = new Date();
    var pickerInline = myApp.picker({
        input: '#ks-picker-day',
        container: '#ks-picker-day-container',
        toolbar: false,
        rotateEffect: true,
        value: [today.getFullYear(), today.getMonth(), today.getDate()],
        onChange: function (picker, values, displayValues) {
            var daysInMonth = new Date(picker.value[2], picker.value[0] * 1 + 1, 0).getDate();
            if (values[1] > daysInMonth) {
                picker.cols[1].setValue(daysInMonth);
            }
        },
        formatValue: function (p, values, displayValues) {
            return values[0] + '-' + values[1] + '-' + displayValues[2];
        },
        cols: [
            // Years
            {
                values: (function () {
                    var arr = [];
                    for (var i = 1950; i <= 2030; i++) {
                        arr.push(i);
                    }
                    return arr;
                })()
            },
            // Months
            {
                values: ('1 2 3 4 5 6 7 8 9 10 11 12').split(' '),
                displayValues: ('January February March April May June July August September October November December').split(' '),
                textAlign: 'left'
            },
            // Days
            {
                values: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31],
            }
        ]
    });
    var pickerInline = myApp.picker({
        input: '#ks-picker-time',
        container: '#ks-picker-time-container',
        toolbar: false,
        rotateEffect: true,
        value: [today.getHours(), (today.getMinutes() < 10 ? '0' + today.getMinutes() : today.getMinutes())],
        onChange: function (picker, values) {
            var daysInMonth = new Date(picker.value[2], picker.value[0] * 1 + 1, 0).getDate();
            if (values[1] > daysInMonth) {
                picker.cols[1].setValue(daysInMonth);
            }
        },
        formatValue: function (p, values) {
            return values[0] + ':' + values[1];
        },
        cols: [
            // Hours
            {
                values: (function () {
                    var arr = [];
                    for (var i = 0; i <= 23; i++) { arr.push(i); }
                    return arr;
                })()
            },
            // Divider
            {
                divider: true,
                content: ':'
            },
            // Minutes
            {
                values: (function () {
                    var arr = [];
                    for (var i = 0; i <= 59; i++) { arr.push(i < 10 ? '0' + i : i); }
                    return arr;
                })()
            }
        ]
    });
});

/**
 * 好友列表
 * @author zhaopeng
 **/
myApp.onPageInit('friend', function (page) {
    $$('.friend').on('click', function () {
        myApp.modal({
            title:  '好友邀请已发送成功！',
            text: '',
            buttons: [
                {
                    text: '了解了',
                    bold: true,
                    onClick: function () {
                        mainView.router.back();
                    }
                }
            ]
        });
    });
});

/**
 * 添加课程
 * @author zhaopeng
 **/
myApp.onPageInit('addCourse', function (page) {
    $$('.courseRepeat-prompt').on('click', function () {
        myApp.modal({
            title: '重复',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="一次性活动" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">一次性活动</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="每天"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">每天</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="每周"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">每周</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="每月"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">每月</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="自定义"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">自定义</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".courseRepeat-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.courseRemind-prompt').on('click', function () {
        myApp.modal({
            title: '提醒',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="无"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">无</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="开始时"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">开始时</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="10分钟前" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">10分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="30分钟前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">30分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="1小时前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">1小时前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="自定义"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">自定义</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".courseRemind-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
});

/**
 * 添加会议
 * @author zhaopeng
 **/
myApp.onPageInit('addActivity', function (page) {
    $$('.activityRepeat-prompt').on('click', function () {
        myApp.modal({
            title: '重复',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="一次性活动" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">一次性活动</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="每天"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">每天</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="每周"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">每周</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="每月"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">每月</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="自定义"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">自定义</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".activityRepeat-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.activityRemind-prompt').on('click', function () {
        myApp.modal({
            title: '提醒',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="无"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">无</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="开始时"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">开始时</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="10分钟前" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">10分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="30分钟前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">30分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="1小时前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">1小时前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="自定义"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">自定义</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".activityRemind-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
});

/**
 * 导入课程
 * @author zhaopeng
 **/
myApp.onPageInit('importCourse', function (page) {
    $$('.import').on('click', function () {
        myApp.showPreloader('正在导入...');
        setTimeout(function () {
            myApp.hidePreloader();
            myApp.modal({
                title:  '导入成功！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            mainView.router.loadPage("week.html");
                        }
                    }
                ]
            });
        }, 2000);
    });
});

/**
 *  talk
 *  @author zhaopeng
 **/
myApp.onPageInit('talk', function (page) {
    var conversationStarted = false;
    var answers = [
        '是的！',
        '不',
        '嗯...',
        '我不确定...',
        '那么你呢？',
        '或许是吧;)',
        '什么？',
        '你确定吗？',
        '当然',
        '需要好好考虑考虑',
        '太惊奇了！！！'
    ];
    var people = [
        {
            name: '凯特',
            avatar: './build/img/9.jpg'
        }
    ];
    var answerTimeout, isFocused;

    // Initialize Messages
    var myMessages = myApp.messages('.messages');

    // Initialize Messagebar
    var myMessagebar = myApp.messagebar('.messagebar');

    $$('.messagebar a.send-message').on('touchstart mousedown', function () {
        isFocused = document.activeElement && document.activeElement === myMessagebar.textarea[0];
    });
    $$('.messagebar a.send-message').on('click', function (e) {
        // Keep focused messagebar's textarea if it was in focus before
        if (isFocused) {
            e.preventDefault();
            myMessagebar.textarea[0].focus();
        }
        var messageText = myMessagebar.value();
        if (messageText.length === 0) {
            return;
        }
        // Clear messagebar
        myMessagebar.clear();

        // Add Message
        myMessages.addMessage({
            text: messageText,
            avatar: './build/img/6.jpg',
            type: 'sent',
            date: 'Now'
        });
        conversationStarted = true;
        // Add answer after timeout
        if (answerTimeout) clearTimeout(answerTimeout);
        answerTimeout = setTimeout(function () {
            var answerText = answers[Math.floor(Math.random() * answers.length)];
            var person = people[Math.floor(Math.random() * people.length)];
            myMessages.addMessage({
                text: answers[Math.floor(Math.random() * answers.length)],
                type: 'received',
                name: person.name,
                avatar: person.avatar,
                date: 'Just now'
            });
        }, 2000);
    });
});

/**
 * 注册
 * @author zhaopeng
 **/
myApp.onPageInit('sign', function (page) {
    $$('.sign-code-prompt').on('click', function () {
        if($$("#signnumber").val()==''||$$("#signnumber").val()==null){
            myApp.modal({
                title: '手机号不能为空！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#signnumber").focus();
                        }
                    }
                ]
            });
            return false;
        }
        myApp.modal({
            title:  '验证码已发送至您的手机，请注意查收！',
            text: '',
            buttons: [
                {
                    text: '了解了',
                    bold: true
                }
            ]
        });
        setTimeout(function () {
            myApp.addNotification({
                message: '您的验证码为：123456',
                button: {
                    text: '关闭',
                    color: 'green'
                }
            });
        }, 2000);
    });
    $$('.sign-prompt').on('click', function () {
        if($$("#signnumber").val()==''||$$("#signnumber").val()==null){
            myApp.modal({
                title: '手机号不能为空！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#signnumber").focus();
                        }
                    }
                ]
            });
            return false;
        }
        if($$("#signpassword").val()==''||$$("#signpassword").val()==null){
            myApp.modal({
                title: '密码不能为空！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#signpassword").focus();
                        }
                    }
                ]
            });
            return false;
        }
        if($$("#signcode").val()==''||$$("#signcode").val()==null||$$("#signcode").val()!='123456'){
            myApp.modal({
                title: '验证码不正确！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#signcode").focus();
                        }
                    }
                ]
            });
            return false;
        }
        myApp.showIndicator();
        setTimeout(function () {
            myApp.hideIndicator();
            myApp.modal({
                title: '注册成功！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick: function () {
                            mainView.router.back();
                        }
                    }
                ]
            });
        }, 2000);
    });
});
/**
 * 忘记密码
 * @author zhaopeng
 **/
myApp.onPageInit('password1', function (page) {
    $$('.reset-code-prompt').on('click', function () {
        if($$("#password1number").val()==''||$$("#password1number").val()==null){
            myApp.modal({
                title: '手机号不能为空！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#password1number").focus();
                        }
                    }
                ]
            });
            return false;
        }
        myApp.modal({
            title:  '验证码已发送至您的手机，请注意查收！',
            text: '',
            buttons: [
                {
                    text: '了解了',
                    bold: true
                }
            ]
        });
        setTimeout(function () {
            myApp.addNotification({
                message: '您的验证码为：123456',
                button: {
                    text: '关闭',
                    color: 'green'
                }
            });
        }, 2000);
    });
    $$('.nextbtn').on('click', function () {
        if($$("#password1number").val()==''||$$("#password1number").val()==null){
            myApp.modal({
                title: '手机号不能为空！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#password1number").focus();
                        }
                    }
                ]
            });
            return false;
        }
        if($$("#password_sign").val()==''||$$("#password_sign").val()==null||$$("#password_sign").val()!='123456'){
            myApp.modal({
                title: '验证码不正确！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#password_sign").focus();
                        }
                    }
                ]
            });
            return false;
        }
        mainView.router.loadPage("password.html");
    });
});
/**
 * 重置密码
 * */
myApp.onPageInit('password', function (page) {
    $$('.save').on('click', function () {
        if($$("#newpassword").val()==''||$$("#newpassword").val()==null){
            myApp.modal({
                title: '新密码不能为空！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#newpassword").focus();
                        }
                    }
                ]
            });
            return false;
        }
        if($$("#sure_password").val()==''||$$("#sure_password").val()==null){
            myApp.modal({
                title: '确认密码不能为空！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#sure_password").focus();
                        }
                    }
                ]
            });
            return false;
        }
        if($$("#newpassword").val()!=$$("#sure_password").val()){
            myApp.modal({
                title: '两次密码输入不相同！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#sure_password").focus();
                        }
                    }
                ]
            });
            return false;
        }
        myApp.showIndicator();
        setTimeout(function () {
            myApp.hideIndicator();
            myApp.modal({
                title: '密码已更改！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick: function () {
                            mainView.router.loadPage("login.html");
                        }
                    }
                ]
            });
        }, 2000);
    });
});

/**
 * 查看活动
 * @author zhaopeng
 **/
myApp.onPageInit('viewActivity', function (page) {
    $$('.viewActivityRemind-prompt').on('click', function () {
        myApp.modal({
            title: '提醒',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="无"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">无</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="开始时"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">开始时</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="10分钟前" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">10分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="30分钟前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">30分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="1小时前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">1小时前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="自定义"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">自定义</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".viewActivityRemind-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.deleteActivity').on('click', function () {
        myApp.modal({
            title:  '您确定要删除此活动吗？',
            text: '',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick: function () {
                        myApp.showIndicator();
                        setTimeout(function () {
                            myApp.hideIndicator();
                            myApp.modal({
                                title:  '删除成功！',
                                text: '',
                                buttons: [
                                    {
                                        text: '确定',
                                        bold: true,
                                        onClick: function () {
                                            mainView.router.back();
                                        }
                                    }
                                ]
                            });
                        }, 1000);
                    }
                }
            ]
        });
    });
});
/**
 * 查看会议
 * @author zhaopeng
 **/
myApp.onPageInit('viewMeeting', function (page) {
    $$('.viewMeetingRemind-prompt').on('click', function () {
        myApp.modal({
            title: '提醒',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="无"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">无</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="开始时"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">开始时</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="10分钟前" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">10分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="30分钟前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">30分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="1小时前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">1小时前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="自定义"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">自定义</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".viewMeetingRemind-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.deleteMeeting').on('click', function () {
        myApp.modal({
            title:  '您确定要删除此会议吗？',
            text: '',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick: function () {
                        myApp.showIndicator();
                        setTimeout(function () {
                            myApp.hideIndicator();
                            myApp.modal({
                                title:  '删除成功！',
                                text: '',
                                buttons: [
                                    {
                                        text: '确定',
                                        bold: true,
                                        onClick: function () {
                                            mainView.router.back();
                                        }
                                    }
                                ]
                            });
                        }, 1000);
                    }
                }
            ]
        });
    });
});
/**
 * 查看课程
 * @author zhaopeng
 **/
myApp.onPageInit('viewCourse', function (page) {
    $$('.viewCourseRemind-prompt').on('click', function () {
        myApp.modal({
            title: '提醒',
            text: '',
            afterText: '<div class="list-block"><ul>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="无"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">无</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="开始时"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">开始时</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="10分钟前" checked="checked"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">10分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="30分钟前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">30分钟前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="1小时前"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">1小时前</div></div>' +
            '</label></li>' +
            '<li><label class="label-radio item-content"><input type="radio" name="ks-radio" value="自定义"/>' +
            '<div class="item-media"><i class="icon icon-form-radio"></i></div>' +
            '<div class="item-inner"><div class="item-title">自定义</div></div>' +
            '</label></li>' +
            '</ul></div>',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick:function () {
                        $$(".viewCourseRemind-prompt").find(".item-after").html($$("input[type='radio']:checked").val());
                    }
                }
            ]
        });
    });
    $$('.deleteCourse').on('click', function () {
        myApp.modal({
            title:  '您确定要删除此课程吗？',
            text: '',
            buttons: [
                {
                    text: '取消'
                },
                {
                    text: '确定',
                    bold: true,
                    onClick: function () {
                        myApp.showIndicator();
                        setTimeout(function () {
                            myApp.hideIndicator();
                            myApp.modal({
                                title:  '删除成功！',
                                text: '',
                                buttons: [
                                    {
                                        text: '确定',
                                        bold: true,
                                        onClick: function () {
                                            mainView.router.back();
                                        }
                                    }
                                ]
                            });
                        }, 1000);
                    }
                }
            ]
        });
    });
});
/**
 * 添加好友
 * @author zhaopeng
 */
myApp.onPageInit('addPeer', function (page) {
    $$('.addPeer').on('click', function () {
        myApp.showIndicator();
        setTimeout(function () {
            myApp.hideIndicator();
            myApp.modal({
                title: '添加成功！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick: function () {
                            mainView.router.back();
                        }
                    }
                ]
            });
        }, 1000);
    });
});

/**
 * 登录验证
 * @author zhaopeng
 **/
myApp.onPageInit('login', function (page) {
    $$('.loginbtn').on('click', function () {
        if($$("#loginnumber").val()==''||$$("#loginnumber").val()==null){
            myApp.modal({
                title: '手机号不能为空！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#loginnumber").focus();
                        }
                    }
                ]
            });
            return false;
        }
        if($$("#loginpassword").val()==''||$$("#loginpassword").val()==null){
            myApp.modal({
                title: '密码不能为空！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick:function () {
                            $$("#loginpassword").focus();
                        }
                    }
                ]
            });
            return false;
        }
        mainView.router.loadPage("month.html");
    });
});
/**
 * 通知
 * @author zhaopeng
 **/
myApp.onPageInit('message_one', function (page) {
    $$('.importMessage').on('click', function () {
        myApp.showIndicator();
        setTimeout(function () {
            myApp.hideIndicator();
            myApp.modal({
                title: '导入成功！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick: function () {
                            mainView.router.back();
                        }
                    }
                ]
            });
        }, 1000);
    });
});
/**
 * 通知
 * @author zhaopeng
 **/
myApp.onPageInit('message_two', function (page) {
    $$('.importMessage').on('click', function () {
        myApp.showIndicator();
        setTimeout(function () {
            myApp.hideIndicator();
            myApp.modal({
                title: '导入成功！',
                text: '',
                buttons: [
                    {
                        text: '了解了',
                        bold: true,
                        onClick: function () {
                            mainView.router.back();
                        }
                    }
                ]
            });
        }, 1000);
    });
});

/**
 * 相册
 **/
$$('.ehead').on('click', function () {
    myApp.modal({
        title: '',
        text: '',
        afterText: '<div class="list-block"><ul>' +
        '<li><a href="#" class="item-link item-content">' +
        '<div class="item-media"><i class="icon material-icons">linked_camera</i></div>' +
        '<div class="item-inner">' +
        '<div class="item-title">拍照</div></div></a></li>' +
        '<li><a href="#" class="item-link item-content photo">' +
        '<div class="item-media"><i class="icon material-icons">image</i></div>' +
        '<div class="item-inner">' +
        '<div class="item-title">相册</div></div></a></li>' +
        '</ul></div>',
        buttons: [
            {
                text: '取消',
                bold: true
            }
        ]
    });
    $$('.photo').on('click', function () {
        myApp.closeModal(".modal");
        myApp.closePanel(".panel-left");
        mainView.router.loadPage("photo.html");
    });
});
myApp.onPageInit('photo', function (page) {
    var swiperTop = myApp.swiper('.ks-swiper-gallery-top', {
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        spaceBetween: 10
    });
    var swiperThumbs = myApp.swiper('.ks-swiper-gallery-thumbs', {
        slidesPerView: 'auto',
        spaceBetween: 10,
        centeredSlides: true,
        touchRatio: 0.2,
        slideToClickedSlide: true
    });
    swiperTop.params.control = swiperThumbs;
    swiperThumbs.params.control = swiperTop;
});

setTimeout(function () {
    $$(".comemessage").append('<i class="material-icons" style="color:red">looks_one</i>');
    myApp.addNotification({
        message: '您收到新通知！',
        button: {
            text: '查看',
            color: 'green',
            onClick: function () {
                myApp.closeModal();
                mainView.router.loadPage("message_one.html");
            }
        }
    });
    setTimeout(function () {
        myApp.closeNotification('.notifications');
    }, 10000);
}, 30000);

setTimeout(function () {
    mainView.router.loadPage("clock.html");
}, 60000);
myApp.onPageInit('clock', function (page) {
    $$('.closeclock').on('click', function () {
        mainView.router.back();
    });
});