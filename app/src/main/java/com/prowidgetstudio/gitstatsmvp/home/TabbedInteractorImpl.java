package com.prowidgetstudio.gitstatsmvp.home;

import com.prowidgetstudio.gitstatsmvp.repository.database.Commits;
import com.prowidgetstudio.gitstatsmvp.webservice.ServiceInterface;
import com.prowidgetstudio.gitstatsmvp.webservice.ServiceWrapper;
import com.prowidgetstudio.gitstatsmvp.webservice.commits.GetCommit;
import com.prowidgetstudio.gitstatsmvp.webservice.commits.ResponseCommits;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dzano on 2.12.2018.
 */

public class TabbedInteractorImpl implements TabbedInteractor {

    private String lastPageSha = "";
    private long kontrolnoVrijeme = 0;
    private int brojPonavljanja = 0;
    private List<Commits> commitsList = new ArrayList<>();
    private boolean zavrseno = false;
    private boolean paginationSha = false;
    private int count = 0;
    private String newLastSha = "";

    @Override
    public void getData(final String token, final String url, final String lastSha, final OnFinishedListener onFinishedListener, final OnZavrsenDownloadListener onZavrsenDownloadListener) {

        ServiceInterface serviceInterface = ServiceWrapper.createService(ServiceInterface.class, token);
        Call<ArrayList<ResponseCommits>> call = null;

        System.out.println(" ");

        if(paginationSha) {
            call = serviceInterface.getCommitsAgain(url, lastPageSha);

        } else {
            call = serviceInterface.getCommits(url);
        }

        call.enqueue(new Callback<ArrayList<ResponseCommits>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseCommits>> call, Response<ArrayList<ResponseCommits>> response) {

                if (response.isSuccessful()) {

                    onFinishedListener.getData(count);

                    System.out.println("broj: " + response.body().size());
                    System.out.println("lastPageSha: " + lastPageSha);
                    System.out.println("###lastSha: " + lastSha);

                    for (int i = 0; i < response.body().size(); i++) {

                        String sha = response.body().get(i).getSha();

                        // novi posljednji SHA
                        if (newLastSha.length() < 5) {
                            newLastSha = sha;
                        }

                        // ako sha vec postoji ili nema vise commit-a, prekidam
                        if (lastSha.equals(sha) || (lastPageSha.equals(sha) && response.body().size()==1)) {

                            // posaljednji unos
                            if(kontrolnoVrijeme != 0) {
                                Commits commits = new Commits();
                                commits.setCommitDate(kontrolnoVrijeme);
                                commits.setBroj(brojPonavljanja);
                                commitsList.add(commits);

                                kontrolnoVrijeme = 0;
                            }

                            zavrseno = true;
                            break;
                        }

                        // kontrola, nema duplih unosa
                        if (!sha.equals(lastPageSha)) {

                            //brojanje
                            count ++;
                            //zabiljezit posljednji sha
                            if (i == (response.body().size() - 1))
                                lastPageSha = sha;

                            GetCommit commit = response.body().get(i).getCommit();
                            String vrijeme = commit.getCommitter().getDate();

                            // vrijeme grupisemo u sate, String -> long
                            String[] datum = vrijeme.split(":");
                            Date date = null;
                            long mills = 0;
                            try {
                                date = new SimpleDateFormat("yyyy-MM-dd'T'hh", Locale.getDefault()).parse(datum[0]);
                                mills = date.getTime();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            // brojanje commit-a u istom satu
                            if (kontrolnoVrijeme == 0) {
                                kontrolnoVrijeme = mills;
                                brojPonavljanja = 1;

                            } else if (mills == kontrolnoVrijeme)
                                brojPonavljanja++;
                            else {

                                Commits commits = new Commits();
                                commits.setCommitDate(kontrolnoVrijeme);
                                commits.setBroj(brojPonavljanja);
                                commitsList.add(commits);

                                kontrolnoVrijeme = mills;
                                brojPonavljanja = 1;
                            }
                        }
                    }

                    if (zavrseno) {
                        paginationSha = false;
                        onZavrsenDownloadListener.onZavrseno(commitsList, newLastSha, count);
                        count = 0;
                        newLastSha = "";
                        commitsList = new ArrayList<>();
                    }
                    else {
                        paginationSha = true;
                        getData(token, url, lastSha, onFinishedListener, onZavrsenDownloadListener);
                    }

                } else {

                    onFinishedListener.errorMsg(false);
                    System.out.println("ERROR: ");
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ResponseCommits>> call, Throwable t) {
                onFinishedListener.errorMsg(true);
                System.out.println("FAILURE: ");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void stopDownload(){
        zavrseno = true;
    }

}
