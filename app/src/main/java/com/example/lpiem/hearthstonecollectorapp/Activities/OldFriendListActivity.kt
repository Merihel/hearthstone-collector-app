package com.example.lpiem.hearthstonecollectorapp.Activities


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.lpiem.hearthstonecollectorapp.Adapter.FriendsListAdapter
import com.example.lpiem.hearthstonecollectorapp.Adapter.PendingFriendsListAdapter
import com.example.lpiem.hearthstonecollectorapp.Manager.APIManager
import com.example.lpiem.hearthstonecollectorapp.Manager.HsUserManager
import com.example.lpiem.hearthstonecollectorapp.Models.Friendship

import com.example.lpiem.hearthstonecollectorapp.R
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_friend_list.view.*
import kotlinx.android.synthetic.main.old_activity_friend_list.*
import kotlinx.android.synthetic.main.fragment_friend_list.view.*
import kotlinx.android.synthetic.main.fragment_pending_friend_list.view.*

class OldFriendListActivity : AppCompatActivity() {

    /**
     * The [androidx.viewpager.widget.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * androidx.fragment.app.FragmentStatePagerAdapter.
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.old_activity_friend_list)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_friend_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //NOT USED
        return true
    }


    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a TabFragment (defined as a static inner class below).
            return TabFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return "SECTION 1"
                1 -> return "SECTION 2"
            }
            return null
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class TabFragment : Fragment() {

        private var hsUserManager = HsUserManager
        private val controller = APIManager()

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            /* TODO Add friend
            friends_toolbar.btn_add_friend.setOnClickListener(

            )
            */

            if (arguments?.getInt(ARG_SECTION_NUMBER) == 1) {
                var rootView = inflater.inflate(R.layout.fragment_friend_list, container, false)
                controller.getFriendshipsByUser(hsUserManager.loggedUser.id!!).observe(this, Observer {
                    addDataToPendingFriendList(it)

                })
                return rootView
            } else {
                var rootView = inflater.inflate(R.layout.fragment_pending_friend_list, container, false)
                controller.getPendingFriendshipsByUser(hsUserManager.loggedUser.id!!).observe(this, Observer {
                    addDataToPendingFriendList(it)
                })
                return rootView
            }
            //rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): TabFragment {
                val fragment = TabFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }

        fun openDeleteDialog(friendship: Friendship) {
            val builder = AlertDialog.Builder(this.requireContext())

            // Set the alert dialog title
            builder.setTitle("Supprimer " + friendship.user2.pseudo)

            // Display a message on alert dialog
            builder.setMessage("Êtes-vous sûr de vouloir supprimer '"+ friendship.user2.pseudo +"' ?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("Oui"){ _, _ ->
                // Do something when user press the positive button
                controller.deleteFriendship(friendship.id).observe(this, Observer {
                    Log.d("onDeleteDone", it.get("exit_code").asString)
                    Toast.makeText(this.requireContext(), it.get("message").asString, Toast.LENGTH_LONG).show()

                })

                if(arguments?.getInt(ARG_SECTION_NUMBER) == 1) {
                    controller.getFriendshipsByUser(hsUserManager.loggedUser.id!!).observe(this, Observer {
                        Log.d("onFriendshipDone", it[0].id.toString())
                        addDataToFriendList(it)
                    })
                } else {
                    controller.getPendingFriendshipsByUser(hsUserManager.loggedUser.id!!).observe(this, Observer {
                        Log.d("onPFriendshipDone", it[0].id.toString())
                        addDataToPendingFriendList(it)
                    })
                }
            }


            // Display a negative button on alert dialog
            builder.setNegativeButton("Non"){ _, _ ->
                Toast.makeText(requireContext(),"Opération annulée",Toast.LENGTH_SHORT).show()
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
        }

        fun addDataToFriendList(res: List<Friendship>): View {
            val listenerFriendList = object : FriendsListAdapter.Listener {
                override fun onItemClicked(item: Friendship) {
                    Log.d("onItemClicked", "Clicked on "+item.user2.pseudo+" !")
                }
                override fun onDeleteClicked(item: Friendship) {
                    Log.d("onDeleteClicked", "Deleting "+item.user2.pseudo+" ?")
                    openDeleteDialog(item)
                }

                override fun onAcceptClicked(item: Friendship) {

                }
            }

            Log.d("addDataToFriendlist", res[0].id.toString())

            var rootView = view!!.rootView

            rootView.rv_friends_list.adapter = FriendsListAdapter(res, getActivity()!!.applicationContext, listenerFriendList)
            rootView.rv_friends_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(getActivity()!!.applicationContext)
            return rootView
        }

        fun addDataToPendingFriendList(res: List<Friendship>): View {
            val listenerPendingFriendList = object : PendingFriendsListAdapter.Listener {
                override fun onItemClicked(item: Friendship) {
                    Log.d("onItemClicked", "Clicked on "+item.user2.pseudo+" !")
                }
                override fun onDeleteClicked(item: Friendship) {
                    Log.d("onDeleteClicked", "Deleting "+item.user2.pseudo+" ?")
                    openDeleteDialog(item)
                }
            }

            Log.d("addDataToFriendlist", res[0].id.toString())

            var rootView = view!!.rootView

            rootView.rv_pending_friends_list.adapter = PendingFriendsListAdapter(res, getActivity()!!.applicationContext, listenerPendingFriendList)
            rootView.rv_pending_friends_list.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(getActivity()!!.applicationContext)
            return rootView
        }
    }
}
