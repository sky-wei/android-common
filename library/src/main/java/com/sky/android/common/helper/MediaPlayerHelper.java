/*
 * Copyright (c) 2018 The sky Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sky.android.common.helper;

import android.media.MediaPlayer;
import android.text.TextUtils;

import com.sky.android.common.util.Alog;

import java.io.IOException;

/**
 * Created by sky on 17-2-13.
 */
public class MediaPlayerHelper {

    private MediaPlayer mMediaPlayer;

    public MediaPlayerHelper() {
        mMediaPlayer = new MediaPlayer();
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public void play(String path) {

        if (TextUtils.isEmpty(path)) return ;

        try {
            // 重置
            stop();
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            Alog.e("播放音频异常", e);
        }
    }

    public boolean isPlay() {
        return mMediaPlayer.isPlaying();
    }

    public void stop() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    public void release() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}
