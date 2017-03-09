package com.mwmurawski.ufcquiz.game;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by Mij on 2017-02-28.
 */

public class DataInitializer {
    public final void initialize(){
        final List<Question> q4List = getQuestions();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for(Question q : q4List){
                    realm.copyToRealm(q);
                }
            }
        });
    }

    /**
     * To add question just add new element in list that include constructor of Question class
     * @return list of questions
     */
    private List<Question> getQuestions(){
        List<Question> list = new ArrayList<>(10);
        list.add(new Question("Who was first champion in UFC?", "Royce Gracie", "Gerard Gordeau", "Ken Shamrock", "Kevin Rosier"));
        list.add(new Question("In what year was UFC 1?", "1993", "1991", "1995", "1996"));
        list.add(new Question("Who was first European champion?", "Bas Rutten", "Andrei Arlovski", "Carlos Newton", "Jens Pulver"));
        list.add(new Question("Where UFC 205 took place?", "New York", "Denver", "Phoenix", "Houston"));
        list.add(new Question("Who was first bantamweight champion?", "Dominic Cruz", "Renan Barao", "Demetrious Johnson", "Jose Aldo"));
        list.add(new Question("What are names of famous Diaz brothers?", "Nate and Nick", "Nate and Conor", "Jose and Conor", "Nick and Brock"));
        list.add(new Question("Who's nick name is Soldier of God", "Yoel Romero", "Luke Rockhold", "Jose Aldo", "Carlos Condit"));
        list.add(new Question("How long are rounds in UFC?", "5 minutes", "2 minutes", "3 minutes", "4 minutes"));
        list.add(new Question("Who's nick name is Spider?", "Anderson Silva", "Chris Weidman", "Carlos Condit", "Vitor Belfort"));
        list.add(new Question("Who never held a title?", "Nick Diaz", "Robbie Lawler", "Johny Hendricks", "Renan Barao"));
        return list;
    }
}
