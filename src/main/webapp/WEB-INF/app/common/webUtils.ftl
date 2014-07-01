<#setting date_format="yyyy-MM-dd">
<#setting datetime_format="yyyy-MM-dd HH:mm:ss">

<#function getTime time="">
    <#if time?is_date>
        <#return time?datetime>
    <#else>
        <#return time>
    </#if>
</#function>

<#function getDate time="">
    <#if time?is_date>
        <#return time?date>
    <#else>
        <#return time>
    </#if>
</#function>

<#macro select type="" list=[] name="name" id="id" value="" class="" style="width:120px" size="" extraProps=""
hasEmpty=false emptyKey="" emptyText="全部" disabled=false onchange="" onclick="" onfocus="" onblur="" hasAuth=false>
    <#if hasAuth>
        <#if type!="" && optionMap["${type}"]??>
            <#list optionMap["${type}"] as item>
                <#local emptyKey = emptyKey + ",'" +item.value + "'">
            </#list>
            <#if (emptyKey?length>0)>
                <#local emptyKey = emptyKey?substring(1)>
            </#if>
        <#elseif (0<list?size) >
            <#list list as item>
                <#local emptyKey = emptyKey +",'" +item.value + "'">
            </#list>
            <#if (emptyKey?length>0)>
                <#local emptyKey = emptyKey?substring(1)>
            </#if>
        </#if>
    </#if>
<select name="${name}" id="${id}" style="${style}" ${extraProps}
    <#rt>
    <#if size!="">
        size="${size}"
    </#if>
    <#rt>
    <#if class!="">
        class="${class}"
    </#if>
    <#rt>
    <#if onchange!="">
        onchange="${onchange}"
        <#rt>
    </#if>
    <#if onclick!="">
        onclick="${onclick}"
        <#rt>
    </#if>
    <#if onfocus!="">
        onfocus="${onfocus}"
        <#rt>
    </#if>
    <#if disabled>
        disabled
        <#rt>
    </#if>
        >
    <#if hasEmpty>
        <option value="${emptyKey?html}">${emptyText?html}</option>
    </#if>
    <#if type!="" && optionMap["${type}"]??>
        <#list optionMap["${type}"] as item>
            <option value="${item.value!}"
                <#rt>
                <#if value==item.value>
                    selected
                    <#rt>
                </#if>
                    >${item.text!}</option>
        </#list>
    <#elseif 0<list?size >
        <#list list as item>
            <option value="${item.value!}"
                <#rt>
                <#if item.value??&&value==item.value>
                    selected
                    <#rt>
                </#if>
                    >${item.text!}</option>
        </#list>
    </#if>
</select>
</#macro>

<#macro pagination id="result">
<div id="${id}">
</div>
<script language="javascript" type="text/javascript">
    $(document).ready(function () {
        var ${id}HasBindInit = false;
        if ($("#${id}").data("events")) {
            if ($("#${id}").data("events")["init"]) {
                ${id}HasBindInit = true;
            }
        }
        if (!${id}HasBindInit) {
            $('#${id}').bind('init', function () {
                var param = $(this).data("param");
                if (param) {
                    var size = param.pageSize || 10;
                    var pageNo = param.pageNo || 1;
                    var url;
                    if (param.url.indexOf('?') != -1) {
                        url = param.url + "&page.id=${id}&page.pageSize=" + size + "&page.pageNo=" + pageNo;
                    } else {
                        url = param.url + "?page.id=${id}&page.pageSize=" + size + "&page.pageNo=" + pageNo;
                    }
                    var queryParam = param.param || {};
                    $.ajax({
                        url: url,
                        type: "POST",
                        data: queryParam,
                        async: false,
                        context: $(this),
                        dataType: 'html',
                        success: function (html) {
                            $(this).html(html);
                        }
                    });
                }
            });
        }
        var ${id}HasBindReload = false;
        if ($("#${id}").data("events")) {
            if ($("#${id}").data("events")["reload"]) {
                ${id}HasBindReload = true;
            }
        }
        if (!${id}HasBindReload) {
            $('#${id}').bind('reload', function () {
                var param = $(this).data("param");
                if (param) {
                    var size = 10;
                    var pageSize = $("#${id} #pageSize").val();
                    if (pageSize) {
                        size = parseInt(pageSize);
                    }

                    var pageNo = 1;
                    var pageNum = $("#${id} .pagination_pageNo").val();
                    if (pageNum) {
                        pageNo = parseInt(pageNum);
                    }
                    var url;
                    if (param.url.indexOf('?') != -1) {
                        url = param.url + "&page.id=${id}&page.pageSize=" + size + "&page.pageNo=" + pageNo;
                    } else {
                        url = param.url + "?page.id=${id}&page.pageSize=" + size + "&page.pageNo=" + pageNo;
                    }
                    var queryParam = param.param || {};
                    $.ajax({
                        url: url,
                        type: "POST",
                        data: queryParam,
                        async: true,
                        context: $(this),
                        dataType: 'html',
                        success: function (html) {
                            $(this).html(html);
                        }
                    });
                }
            })
        }
    })
</script>
</#macro>

<#macro pageInfo>
<div class="pull-right">
    <input type='hidden' class='pagination_totalCount' value='#{page.totalCount}'/>
    <input type='hidden' class='pagination_totalPage' value='#{page.totalPage}'/>
    <input type='hidden' class='pagination_pageNo' value='#{page.pageNo}'/>
    <div class="col-md-6">
        <span>
        共计 <font color="red"><b>#{page.total}</b></font> 条记录<#rt>
            <font color="red"> <b>#{page.totalPages}</b></font> 页&nbsp;每页&nbsp;
            <#local pageSizeList=[{"value":10,"text":10}
            ,{"value":20,"text":20}
            ,{"value":50,"text":50}
            ,{"value":100,"text":100}]>
            <#local jumpFnc = "T.jump('"+(page.id!)+"' ,1)">
            <@select id="pageSize" list=pageSizeList onchange=jumpFnc value=page.pageSize
            style="width:50px;height:22px;padding:0px;margin:2px 0px 4px;" />
            &nbsp;&nbsp;条记录
        </span>
    </div>

    <div class="col-md-6">
        <ul class="pagination">
            <li <#if page.pageNo == 1> class="active" </#if>><a href="javascript:void(0);"
                                                                <#if (page.pageNo > 1)>onclick="T.jump('${page.id!}',1);"</#if> >首页</a>
            </li>
            <li <#if page.pageNo == 1> class="active" </#if>><a href="javascript:void(0);"
                                                                <#if (page.pageNo > 1)>onclick="T.jump('${page.id!}',#{page.pageNo - 1});"</#if> >上一页</a>
            </li>
            <#if (page.totalPage > 0)>
                <#list 1..page.totalPage as i>
                    <#if i==page.pageNo>
                        <li class="active"><a href="javascript:void(0);">${i}</a></li>
                    <#else>
                        <#if i==1|| i==2||i==page.totalPage||i==page.totalPage-1 || i==page.pageNo+1||i==page.pageNo+2||i==page.pageNo-1||i==page.pageNo-2 >
                            <li><a href="javascript:void(0);" onclick="T.jump('${page.id!}',#{i});">${i}</a></li>
                        <#elseif i == page.pageNo - 3 || i == page.pageNo + 3>
                            <li class="active"><a href="javascript:void(0);">...</a></li>
                        </#if>
                    </#if>
                </#list>
            </#if>
            <li <#if (page.pageNo == page.totalPage || page.totalPage == 0) > class="active" </#if>>
                <a href="javascript:void(0);"
                   <#if (page.pageNo < page.totalPage && page.totalPage > 0 )>onclick="T.jump('${page.id!}',#{page.pageNo + 1});"</#if> >下一页</a>
            </li>
            <li <#if (page.pageNo == page.totalPage || page.totalPage == 0) > class="active" </#if>>
                <a href="javascript:void(0);"
                   <#if (page.pageNo < page.totalPage && page.totalPage > 0 )>onclick="T.jump('${page.id!}',#{page.totalPage});"</#if> >末页</a>
            </li>
        </ul>
    </div>
</div>
<div class="clear"></div>
</#macro>