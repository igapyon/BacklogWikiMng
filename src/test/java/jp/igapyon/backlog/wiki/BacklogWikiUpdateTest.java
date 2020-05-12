package jp.igapyon.backlog.wiki;

import java.io.File;

import org.junit.Test;

import jp.igapyon.md2tagmd.Md2TagMd;

public class BacklogWikiUpdateTest {
    @Test
    public void test001() throws Exception {
        System.err.println("Update Wiki begin.");

        BacklogConnection bklConn = BacklogUtil.login( //
                MyBacklogSettings.SPACE_KEY //
                , MyBacklogSettings.API_KEY //
                , MyBacklogSettings.PROJECT_ID);

        BacklogWiki2Local wiki2local = new BacklogWiki2Local(bklConn);

        wiki2local.process(new File("./target/backlog-wiki-update"));

        new Md2TagMd( //
                new File("./target/backlog-wiki-update"), //
                new File("./target/backlog-wiki-update.out")) //
                        .process();

        BacklogLocal2Wiki local2wiki = new BacklogLocal2Wiki(bklConn);
        local2wiki.process(new File("./target/backlog-wiki-update.out"));

        System.err.println("Update Wiki end.");
    }
}
