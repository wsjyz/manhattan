
var baseUrl='/mhd',
	resource={
	'login':baseUrl+'/login',
    'register':baseUrl+'/register',
    'main':baseUrl+'/main'

};

$.fn.serializeJson=function(){
    var serializeObj={};
    var array=this.serializeArray();
    var str=this.serialize();
    $(array).each(function(){
        if(serializeObj[this.name]){
            if($.isArray(serializeObj[this.name])){
                serializeObj[this.name].push(this.value);
            }else{
                serializeObj[this.name]=[serializeObj[this.name],this.value];
            }
        }else{
            serializeObj[this.name]=this.value;
        }
    });
    return serializeObj;
};

$.fn.twbsPagination.defaults.first='首页';
$.fn.twbsPagination.defaults.prev='前一页';
$.fn.twbsPagination.defaults.next='后一页';
$.fn.twbsPagination.defaults.last='最后一页';

$(function(){

})