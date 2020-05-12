/*
 * Copyright 2020 Toshiki Iga
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.igapyon.backlog.wiki;

import java.io.File;
import java.io.IOException;

import com.nulabinc.backlog4j.ResponseList;
import com.nulabinc.backlog4j.Wiki;
import com.nulabinc.backlog4j.api.option.UpdateWikiParams;

/**
 * Backlog Wiki をローカルファイルで更新します。
 * 
 * @author Toshiki Iga
 */
public class BacklogLocal2Wiki {
    private BacklogConnection bklConn = null;

    public BacklogLocal2Wiki(BacklogConnection bklConn) {
        this.bklConn = bklConn;
    }

    /**
     * 指定のディレクトリの Wiki ファイルで更新します。
     * 
     * @param inputdir Wiki 書き出し先ディレクトリ。
     * @throws IOException IO例外が発生した場合。
     */
    public void process(final File inputdir) throws IOException {
        if (bklConn.getClient() == null || bklConn.getProject() == null) {
            throw new IllegalArgumentException("Not connected to Backlog. Please login() before process().");
        }

        Wiki wikiTarget = findWiki("Index");

        UpdateWikiParams params = new UpdateWikiParams(wikiTarget.getId());
        params.content("Ugeuge");
        bklConn.getClient().updateWiki(params);
    }

    protected Wiki findWiki(String name) {
        ResponseList<Wiki> wikiList = bklConn.getClient().getWikis(bklConn.getProject().getId());
        for (Wiki wikiLookup : wikiList) {
            if (name.equals(wikiLookup.getName())) {
                return wikiLookup;
            }
        }

        return null;
    }
}
