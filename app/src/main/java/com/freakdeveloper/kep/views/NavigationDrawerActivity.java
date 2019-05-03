package com.freakdeveloper.kep.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.freakdeveloper.kep.R;
import com.freakdeveloper.kep.fragments.DueloFragment;
import com.freakdeveloper.kep.fragments.GraficasFragment;
import com.freakdeveloper.kep.fragments.PerfilFragment;
import com.freakdeveloper.kep.fragments.PreguntasPorMateriaFragment;
import com.freakdeveloper.kep.fragments.RankingFragment;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PerfilFragment.OnFragmentInteractionListener, DueloFragment.OnFragmentInteractionListener, RankingFragment.OnFragmentInteractionListener, GraficasFragment.OnFragmentInteractionListener, PreguntasPorMateriaFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new PerfilFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content_main,fragment).commit();
        Intent Login = getIntent();
        Bundle Datos = Login.getExtras();
        String ID= Datos.getString("ID");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        Fragment fragment = null;
        Boolean fragmentSeleccionado = false;

        if (id == R.id.perfil) {
            fragment = new PerfilFragment();
            fragmentSeleccionado = true;

        } else if (id == R.id.duelo) {
            fragment = new DueloFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.ranking) {
            fragment = new RankingFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.graficas) {
            fragment = new GraficasFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.preguntas) {
            fragment = new PreguntasPorMateriaFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.cerrarSesion) {
            FirebaseAuth.getInstance().signOut();
            Intent Menu = new Intent(this, PrincipalActivity.class);
            startActivity(Menu);
        } else if (id == R.id.duda) {
            Intent Duda = new Intent(this, DudaActivity.class);
            startActivity(Duda);
        }


        if(fragmentSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
