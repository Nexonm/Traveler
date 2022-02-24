package we.itschool.project.traveler.data.repositoryImpl;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.data.NewData;
import we.itschool.project.traveler.domain.entity.Person;
import we.itschool.project.traveler.domain.repository.PersonDomainRepository;

public class PersonArrayListRepositoryImpl implements PersonDomainRepository {

    private final MutableLiveData<ArrayList<Person>> dataLiveData;
    private final ArrayList<Person> data;

    {
        data = new ArrayList<>();
        dataLiveData = new MutableLiveData<>();
    }

    private static int autoIncrementId = 0;

    private static final int NUM_OF_PEOPLE_TO_DO;

    static {
        NUM_OF_PEOPLE_TO_DO = 10;
    }

    {
        for (int i = 0; i < NUM_OF_PEOPLE_TO_DO; i++) {
            personAddNew(
                    NewData.newPerson()
            );

        }
    }

    private void updateLiveData() {
        dataLiveData.postValue(new ArrayList<>(data));
        if (AppStart.isLog) {
            Log.w("PersonArrayListRepositoryImpl", data.size() + "\n");
        }
    }

    @Override
    public void personAddNew(Person person) {
        if (person.get_id() == Person.UNDEFINED_ID)
            person.set_id(autoIncrementId++);
        data.add(person);
        updateLiveData();
    }

    @Override
    public void personDeleteById(Person person) {
        data.remove(person);
        updateLiveData();
    }

    @Override
    public void personEditById(Person person) {
        Person person_old = personGetById(person.get_id());
        personDeleteById(person_old);
        personAddNew(person);
    }

    @Override
    public MutableLiveData<ArrayList<Person>> personGetAll() {
        return dataLiveData;
    }

    @Override
    public Person personGetById(int id) {
        Person person = data.get(id);
        if (person != null)
            return person;
        else throw new RuntimeException("There is no element with id = " + id);
    }
}
