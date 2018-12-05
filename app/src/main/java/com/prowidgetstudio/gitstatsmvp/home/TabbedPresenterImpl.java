package com.prowidgetstudio.gitstatsmvp.home;

import com.prowidgetstudio.gitstatsmvp.eventBus.RefreshTabsEventBus;
import com.prowidgetstudio.gitstatsmvp.repository.Repository;
import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;
import org.greenrobot.eventbus.EventBus;
import java.util.List;

/**
 * Created by Dzano on 2.12.2018.
 */

public class TabbedPresenterImpl implements TabbedPresenter{

    TabbedView tabbedView;
    TabbedInteractor tabbedInteractor;
    Repository repository;

    public TabbedPresenterImpl(TabbedView tabbedView, TabbedInteractor tabbedInteractor, Repository repository) {
        this.tabbedView = tabbedView;
        this.tabbedInteractor = tabbedInteractor;
        this.repository = repository;
    }

    @Override
    public void downloadData(boolean firstRun) {

        String token = repository.readToken();
        String url = repository.readString("gitURL");
        String lastSha = repository.readString("lastSha");

        if(firstRun)
            tabbedView.showLoading(0);

        tabbedInteractor.getData(token, url, lastSha, new TabbedInteractor.OnFinishedListener() {
            @Override
            public void getData(int count) {
                tabbedView.showLoading(count);
            }

            @Override
            public void errorMsg(boolean noAccess) {

                tabbedView.hideLoading();
                if(noAccess)
                    tabbedView.noAccess();
            }
        }, new TabbedInteractor.OnZavrsenDownloadListener() {
            @Override
            public void onZavrseno(List<Commits> commitsList, String newLastSha, int count) {

                int commitsCount = repository.readInt("commitsCount");
                // spremi database i zabiljezi pocetni SHA
                try{
                    repository.appendData(commitsList);

                    if(newLastSha != null && newLastSha.length()>5) {
                        repository.saveString("lastSha", newLastSha);
                        repository.saveLong("lastUpdate", System.currentTimeMillis());
                    }
                    repository.saveInt("commitsCount", (commitsCount + count));

                    EventBus.getDefault().post(new RefreshTabsEventBus("Refresh"));


                }catch (Exception e){
                    e.printStackTrace();
                }

                tabbedView.hideLoading();
            }
        });
    }

    @Override
    public void getInfoData(){

        String fullName = repository.readString("fullName");
        String description = repository.readString("description");
        String readMe = repository.readReadMe();

        tabbedView.showProjectInfo(fullName, description, readMe);

        if(repository.readString("lastSha").length() < 5)
            downloadData(true);  // prvo pokretanje, skini podatke
    }

    @Override
    public void stopDownload(){
        tabbedInteractor.stopDownload();
    }

    @Override
    public void logOut(){
        repository.saveString("lastSha", "");
        repository.saveString("token", "");
        repository.saveString("fullName", "");
        repository.saveString("description", "");
        repository.saveString("content", "");
        repository.saveBoolean("loggedin", false);
        repository.saveInt("commitsCount", 0);
        repository.deleteData();

        tabbedView.logOut();
    }
}
