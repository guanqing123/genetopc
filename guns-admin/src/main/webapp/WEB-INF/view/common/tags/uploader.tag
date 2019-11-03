<div class="row">
    <div class="col-sm-12">
        <div class="ibox float-e-margins">
                <div class="page-container">
                    <div id="${id}" class="webuploader wu-example">
                        <div class="queueList">
                            <div id="${id}Area" class="placeholder">
                                <div id="${id}Picker"></div>
                                <p>或将照片拖到这里，单次最多可选${fileNumLimit}张</p>
                            </div>
                        </div>
                        <div class="statusBar" style="display:none;">
                            <div class="progress">
                                <span class="text">0%</span>
                                <span class="percentage"></span>
                            </div>
                            <div class="info"></div>
                            <div class="btns">
                                <div class="uploadBtn">开始上传</div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    </div>
</div>
<script>
    var imagesUp = new $WebUploadMult({
    	pictureId: "${id}",
    	uploadUrl: "${uploadUrl}",
    	fileNumLimit: ${fileNumLimit!1},
    	fileSizeLimit: ${fileSizeLimit!5},
    	fileSingleSizeLimit: ${fileSingleSizeLimit!1}
    });
    imagesUp.init();
</script>

