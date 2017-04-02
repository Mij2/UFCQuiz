package com.mwmurawski.ufcquiz.game;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Mij on 2017-02-28.
 */

/**
 * Class helps to retrieve data from Realm database
 */
public class DataInitializer {
    private Realm realm;

    /**
     * Initialize realm field
     */
    private void init(){
        realm = Realm.getDefaultInstance();
    }

    /**
     * Checks if there is incoherence in database, if it is or db is empty,
     * makes transaction and set list of questions to database.
     */
    public void initialize() {
        init();
        final List<Question> qList = getQuestions();

        //check size of list and db table and clear if there is incoherence
        final RealmResults<Question> dbList = realm.where(Question.class).findAll();
        if (isDbIncoherence(qList.size(), dbList.size())){
            clearDb(dbList);
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(qList);
                }
            });
        }
    }

    /**
     * Clear all records in provided list from Realm database.
     */
    private void clearDb(final RealmResults dbList) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                dbList.deleteAllFromRealm();
            }
        });
    }

    /**
     * Check if database is incoherence with provided list of questions.
     * It check sizes of list from method and size of list from database.
     * @param sizeOfList - size of provided list
     * @param sizeOfDbTable- size of record in database
     * @return returns true if sizes are not equal, false if sizes are equal.
     */
    private boolean isDbIncoherence(final Integer sizeOfList, final Integer sizeOfDbTable){
        return sizeOfList.compareTo(sizeOfDbTable) != 0;

    }

    /**
     * To add question just add new element in list that include constructor of Question class.
     * App will automatically check size of list and size of database, in case of incoherence, db will update all questions.
     * @return list of questions
     */
    private static List<Question> getQuestions() {
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
