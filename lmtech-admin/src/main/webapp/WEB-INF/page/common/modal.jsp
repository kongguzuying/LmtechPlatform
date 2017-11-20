<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- modal -->
<div aria-hidden="true" role="dialog" tabindex="-1" id="myModal" class="modal"
     style="display: none; overflow: hidden;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" class="close" type="button">×</button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body" style="padding: 0px;">
                <iframe id="modalFrame" name="modalFrame" frameborder="none" width="100%" height="100%"
                        scrolling="yes"></iframe>
            </div>
            <div class="modal-footer" style="margin-top: 0px;">
                <button type="button" class="btn btn-success" onclick="okClick()">保&nbsp;&nbsp;存</button>
                <button type="button" class="btn btn-default" onclick="cancelClick()">关&nbsp;&nbsp;闭</button>
            </div>
        </div>
    </div>
</div>

<div aria-hidden="true" role="dialog" tabindex="-1" id="viewModal" class="modal"
     style="display: none; overflow: hidden;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button aria-hidden="true" class="close" type="button">×</button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body" style="padding: 0px;">
                <iframe id="viewModalFrame" name="viewModalFrame" frameborder="none" width="100%" height="100%"
                        scrolling="yes"></iframe>
            </div>
        </div>
    </div>
</div>