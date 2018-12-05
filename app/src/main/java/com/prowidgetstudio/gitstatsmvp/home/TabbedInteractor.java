package com.prowidgetstudio.gitstatsmvp.home;

import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;
import java.util.List;

/**
 * Created by Dzano on 2.12.2018.
 */

public interface TabbedInteractor {

    void getData(String token, String url, String lastSha,
               OnFinishedListener onFinishedListener,
                 OnZavrsenDownloadListener onZavrsenDownloadListener);

    interface OnFinishedListener {
        void getData(int count);
        void errorMsg(boolean noAccess);
    }

    interface OnZavrsenDownloadListener {
        void onZavrseno(List<Commits> commitsList, String newLastSha, int count);
    }

    void stopDownload();
}
