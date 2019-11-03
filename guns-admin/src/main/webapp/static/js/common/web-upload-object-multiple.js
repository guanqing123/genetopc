(function () {

    var $WebUploadMult = function (conf) {
        this.uploaderId = conf.uploaderId;
        this.uploadPreId = this.uploaderId + "Picker";
        this.uploadUrl = Feng.ctxPath + conf.uploadUrl;
        this.fileNumLimit = conf.fileNumLimit;
        this.fileSizeLimit = (conf.fileSizeLimit || 5) * 1024 * 1024;
        this.fileSingleSizeLimit = (conf.fileSingleSizeLimit || 1) * 1024 * 1024;
        var $ = jQuery,
            $wrap = $('#' + this.uploaderId);
        // 图片容器
        this.queue = $('<ul class="filelist"></ul>')
            .appendTo($wrap.find('.queueList'));
        // 状态栏，包括进度和控制按钮
        var $statusBar = $wrap.find('.statusBar');
        this.statusBar = $statusBar;
        // 文件总体选择信息。
        this.info = $statusBar.find('.info');
        // 上传按钮
        this.upload = $wrap.find('.uploadBtn');
        // 没选择文件之前的内容。
        this.placeHolder = $wrap.find('.placeholder');
        // 总体进度条
        this.progress = $statusBar.find('.progress').hide();
        // 添加的文件数量
        this.fileCount = 0;
        // 添加的文件总大小
        this.fileSize = 0;
        // 优化retina, 在retina下这个值是2
        var ratio = window.devicePixelRatio || 1;
        // 缩略图大小
        this.thumbnailWidth = 110 * ratio;
        this.thumbnailHeight = 110 * ratio;
        // 可能有pedding, ready, uploading, confirm, done.
        this.state = 'pedding';
        // 所有文件的进度信息，key为file id
        this.percentages = {};
        this.supportTransition = (function () {
            var s = document.createElement('p').style,
                r = 'transition' in s ||
                    'WebkitTransition' in s ||
                    'MozTransition' in s ||
                    'msTransition' in s ||
                    'OTransition' in s;
            s = null;
            return r;
        })();

    };

    $WebUploadMult.prototype = {
        /**
         * 初始化webUploader
         */
        init: function () {
            var uploader = this.create();
            uploader.addButton({
                id: '#' + this.uploaderId + 'Picker2',
                label: '继续添加'
            });
            this.bindEvent(uploader);
            this.upload.addClass('state-' + this.state);
            this.updateTotalProgress();
            return uploader;
        },

        addFile: function (file) { // 当有文件添加进来时执行，负责view的创建
            var $li = $('<li id="' + file.id + '">' +
                '<p class="title">' + file.name + '</p>' +
                '<p class="imgWrap"></p>' +
                '<p class="progress"><span></span></p>' +
                '</li>'),

                $btns = $('<div class="file-panel">' +
                    '<span class="cancel">删除</span>' +
                    '<span class="rotateRight">向右旋转</span>' +
                    '<span class="rotateLeft">向左旋转</span></div>').appendTo($li),
                $prgress = $li.find('p.progress span'),
                $wrap = $li.find('p.imgWrap'),
                $info = $('<p class="error"></p>'),

                showError = function (code) {
                    switch (code) {
                        case 'exceed_size':
                            text = '文件大小超出';
                            break;

                        case 'interrupt':
                            text = '上传暂停';
                            break;

                        default:
                            text = '上传失败，请重试';
                            break;
                    }

                    $info.text(text).appendTo($li);
                };

            if (file.getStatus() === 'invalid') {
                showError(file.statusText);
            } else {
                $wrap.text('预览中');
                this.makeThumb(file, function (error, src) {
                    if (error) {
                        $wrap.text('不能预览');
                        return;
                    }

                    var img = $('<img src="' + src + '">');
                    $wrap.empty().append(img);
                }, this.thumbnailWidth, this.thumbnailHeight);

                this.percentages[file.id] = [file.size, 0];
                file.rotation = 0;
            }

            file.on('statuschange', function (cur, prev) {
                if (prev === 'progress') {
                    $prgress.hide().width(0);
                } else if (prev === 'queued') {
                    $li.off('mouseenter mouseleave');
                    $btns.remove();
                }

                // 成功
                if (cur === 'error' || cur === 'invalid') {
                    console.log(file.statusText);
                    showError(file.statusText);
                    this.percentages[file.id][1] = 1;
                } else if (cur === 'interrupt') {
                    showError('interrupt');
                } else if (cur === 'queued') {
                    this.percentages[file.id][1] = 0;
                } else if (cur === 'progress') {
                    $info.remove();
                    $prgress.css('display', 'block');
                } else if (cur === 'complete') {
                    $li.append('<span class="success"></span>');
                }

                $li.removeClass('state-' + prev).addClass('state-' + cur);
            });

            $li.on('mouseenter', function () {
                $btns.stop().animate({height: 30});
            });

            $li.on('mouseleave', function () {
                $btns.stop().animate({height: 0});
            });

            $btns.on('click', 'span', function () {
                var index = $(this).index(),
                    deg;

                switch (index) {
                    case 0:
                        this.removeFile(file);
                        return;
                    case 1:
                        file.rotation += 90;
                        break;

                    case 2:
                        file.rotation -= 90;
                        break;
                }

                if (this.supportTransition) {
                    deg = 'rotate(' + file.rotation + 'deg)';
                    $wrap.css({
                        '-webkit-transform': deg,
                        '-mos-transform': deg,
                        '-o-transform': deg,
                        'transform': deg
                    });
                } else {
                    $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
                }
            });
            $li.appendTo(this.queue);
        },

        removeFile: function (file) { // 负责view的销毁
            var $li = $('#' + file.id);
            delete this.percentages[file.id];
            this.updateTotalProgress();
            $li.off().find('.file-panel').off().end().remove();
        },

        updateTotalProgress: function () {
            var loaded = 0,
                total = 0,
                spans = this.progress.children(),
                percent;

            $.each(this.percentages, function (k, v) {
                total += v[0];
                loaded += v[0] * v[1];
            });

            percent = total ? loaded / total : 0;

            spans.eq(0).text(Math.round(percent * 100) + '%');
            spans.eq(1).css('width', Math.round(percent * 100) + '%');
            this.updateStatus();
        },

        updateStatus: function () {
            var text = '', stats;

            if (state === 'ready') {
                text = '选中' + this.fileCount + '张图片，共' +
                    WebUploader.formatSize(this.fileSize) + '。';
            } else if (state === 'confirm') {
                stats = this.getStats();
                if (stats.uploadFailNum) {
                    text = '已成功上传' + stats.successNum + '张照片，' +
                        stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
                }

            } else {
                stats = this.getStats();
                text = '共' + this.fileCount + '张（' +
                    WebUploader.formatSize(this.fileSize) +
                    '），已上传' + stats.successNum + '张';

                if (stats.uploadFailNum) {
                    text += '，失败' + stats.uploadFailNum + '张';
                }
            }

            this.info.html(text);
        },

        setState: function (val) {
            var stats;

            if (val === this.state) {
                return;
            }

            this.upload.removeClass('state-' + this.state);
            this.upload.addClass('state-' + val);
            this.state = val;

            switch (state) {
                case 'pedding':
                    this.placeHolder.removeClass('element-invisible');
                    this.queue.parent().removeClass('filled');
                    this.queue.hide();
                    this.statusBar.addClass('element-invisible');
                    this.refresh();
                    break;

                case 'ready':
                    this.placeHolder.addClass('element-invisible');
                    $('#' + this.uploaderId + 'Picker2').removeClass('element-invisible');
                    this.queue.parent().addClass('filled');
                    this.queue.show();
                    this.statusBar.removeClass('element-invisible');
                    this.refresh();
                    break;

                case 'uploading':
                    $('#' + this.uploaderId + 'Picker2').addClass('element-invisible');
                    this.progress.show();
                    this.upload.text('暂停上传');
                    break;

                case 'paused':
                    this.progress.show();
                    this.upload.text('继续上传');
                    break;

                case 'confirm':
                    this.progress.hide();
                    this.upload.text('开始上传').addClass('disabled');

                    stats = this.getStats();
                    if (stats.successNum && !stats.uploadFailNum) {
                        this.setState('finish');
                        return;
                    }
                    break;
                case 'finish':
                    stats = this.getStats();
                    if (stats.successNum) {
                        alert('上传成功');
                    } else {
                        // 没有成功的图片，重设
                        this.state = 'done';
                        location.reload();
                    }
                    break;
            }

            this.updateStatus();
        },

        /**
         * 创建webuploader对象
         */
        create: function () {
            if (!WebUploader.Uploader.support()) {
                alert('Web Uploader 不支持您的浏览器！如果你使用的是IE浏览器，请尝试升级 flash 播放器');
                throw new Error('WebUploader does not support the browser you are using.');
            }
            var webUploader = WebUploader.create({
                pick: {
                    id: '#' + this.uploadPreId,
                    label: '点击选择图片'
                },
                dnd: '#' + this.uploaderId + ' .queueList',
                paste: document.body,
                accept: {
                    title: 'Images',
                    extensions: 'gif,jpg,jpeg,bmp,png',
                    mimeTypes: 'image/*'
                },
                // swf文件路径
                swf: Feng.ctxPath + '/static/js/plugins/webuploader/Uploader.swf',
                disableGlobalDnd: true,
                chunked: true,
                server: this.uploadUrl,
                fileNumLimit: this.fileNumLimit,
                fileSizeLimit: this.fileSizeLimit,    // 所有文件
                fileSingleSizeLimit: this.fileSingleSizeLimit,   // 单个文件
            });

            return webUploader;
        },

        /**
         * 绑定事件
         */
        bindEvent: function (uploader) {
            var me = this;
            uploader.onUploadProgress = function (file, percentage) {
                var $li = $('#' + file.id),
                    $percent = $li.find('.progress span');

                $percent.css('width', percentage * 100 + '%');
                me.percentages[file.id][1] = percentage;
                me.updateTotalProgress();
            };
            uploader.onFileQueued = function (file) {
                me.fileCount++;
                me.fileSize += file.size;
                if (me.fileCount === 1) {
                    me.placeHolder.addClass('element-invisible');
                    me.statusBar.show();
                }
                me.addFile(file);
                me.setState('ready');
                me.updateTotalProgress();
            };
            uploader.onFileDequeued = function (file) {
                me.fileCount--;
                me.fileSize -= file.size;
                if (!me.fileCount) {
                    me.setState('pedding');
                }
                me.removeFile(file);
                me.updateTotalProgress();
            };
            uploader.on('all', function (type) {
                switch (type) {
                    case 'uploadFinished':
                        me.setState('confirm');
                        break;
                    case 'startUpload':
                        me.setState('uploading');
                        break;
                    case 'stopUpload':
                        me.setState('paused');
                        break;
                }
            });
            uploader.onError = function (code) {
                alert('Eroor: ' + code);
            };
            me.upload.on('click', function () {
                if ($(this).hasClass('disabled')) {
                    return false;
                }

                if (state === 'ready') {
                    uploader.upload();
                } else if (state === 'paused') {
                    uploader.upload();
                } else if (state === 'uploading') {
                    uploader.stop();
                }
            });
            me.info.on('click', '.retry', function () {
                uploader.retry();
            });
            me.info.on('click', '.ignore', function () {
                alert('todo');
            });
        }
    };

    window.$WebUploadMult = $WebUploadMult;

}());