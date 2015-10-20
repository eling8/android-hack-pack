package com.example.emilyling.todolist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.util.*;

public class MainActivity extends ActionBarActivity {
    private ArrayList<String> tasksList;
    private ArrayAdapter<String> taskAdapter;
    private ListView list;

    /* Method: addTaskClick
     * Called when you click the "ADD" button. Adds whatever text is currently
     * entered into the text box to the ArrayList of tasks.
     */
    public void addTaskClick(View view) {
        // Locate the TextView by the id specified in activity_main.xml: 'add'
        TextView addTask = (TextView)findViewById(R.id.add);
        // Get the value from the TextView by calling getText().toString() on it.
        String taskText = addTask.getText().toString();

        if (taskText.isEmpty()) {
            // Toasts let you create pop-up windows for the user -- for example, to give error messages.
            Toast.makeText(this, "Cannot enter empty task.", Toast.LENGTH_SHORT).show();
        } else {
            tasksList.add(taskText);
            // Call notifyDataSetChanged() to let the app know that something has changed
            // and it should update the display.
            taskAdapter.notifyDataSetChanged();
            closeKeyboard();

            // Clear the TextView by calling setText to set it to an empty string.
            addTask.setText("");
        }
    }

    /* Method: closeKeyboard
     * Hides the keyboard if it's open -- don't worry about the details here :)
     */
    private void closeKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /* Method: onCreate
     * This method is called when the activity is created. This is a good place to initialize
     * variables, set action listeners, and restore saved states.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the content view to activity_main.xml

        if (savedInstanceState != null) { // If we previously have something saved, open it!
            tasksList = savedInstanceState.getStringArrayList("list");
        } else { // Otherwise, initialize our ArrayList and add some initial tasks.
            tasksList = new ArrayList<String>();
            tasksList.add("Press and hold to delete me!");
            tasksList.add("Add your own task by typing below and clicking ADD.");
            tasksList.add("Take a look at activity_main.xml to see the layout for this app.");
            tasksList.add("Now look at MainActivity.java to see how it works!");
        }
        // An Adapter object helps to connect the AdapterView, which displays data, with the
        // underlying data. An ArrayAdapter is one kind of Adapter where the underlying data
        // is an array or ArrayList. We use this to connect our list of tasks, tasksList, to
        // the ListView object in activity_main.xml that displays the tasks.
        taskAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tasksList);
        list = (ListView)findViewById(R.id.tasks);
        list.setAdapter(taskAdapter); // Assign the adapter to our ListView

        // We can use action listeners to tell the app what to do when some action happens.
        // In this case, we use setOnItemLongClickListener to specify what to do when someone
        // long clicks on a list item.
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id) {
                return onLongListItemClick(v, pos, id);
            }
        });
    }

    /* Method: onLongListItemClick
     * This method is called whenever a list item is long clicked, and removes the
     * item from the ArrayList and updates the display. Play around with this method
     * to change what happens when someone presses and holds on an item.
     */
    protected boolean onLongListItemClick(View v, int pos, long id) {
        tasksList.remove(pos);
        taskAdapter.notifyDataSetChanged();
        return true;
    }

    /* Method: onSaveInstanceState
     * This method is called whenever you quit or leave the app -- it saves the
     * ArrayList, tasksList, to the outState and saves it under the keyword "list"
     * so that you can retrieve it later.
     */
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("list", tasksList);
    }

    /* Method: onResume
     * This method is called whenever you resume the app -- it retrieves the ArrayList
     * of tasks that was saved in the method onSaveInstancesState above.
     */
    protected void onResume(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        tasksList = savedInstanceState.getStringArrayList("list");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
