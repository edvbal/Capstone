package com.telesoftas.edvinas.onboardingedvblk.main;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class MainPresenterImplTest {

    private static final int SETTINGS_MENU_ITEM_ONE = 1;
    private static final int SETTINGS_MENU_ITEM_TWO = 2;
    private MainView view;
    private MainPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        view = mock(MainView.class);
        presenter = new MainPresenterImpl();
        presenter.takeView(view);
    }

    @Test
    public void onNavigationitemSelected_itemsEqual_callsViewOpenSettings() throws Exception {
        presenter.onNavigationItemSelected(SETTINGS_MENU_ITEM_ONE, SETTINGS_MENU_ITEM_ONE);

        verify(view).openSettings();
    }

    @Test
    public void onNavigationitemSelected_itemsNotEqual_callsViewCloseNavigation() throws Exception {
        presenter.onNavigationItemSelected(SETTINGS_MENU_ITEM_ONE, SETTINGS_MENU_ITEM_TWO);

        verify(view).closeNavigation();
    }

    @Test
    public void onNavigationitemSelected_hasNoView_doesNotCallViewMethods() throws Exception {
        presenter.dropView();

        presenter.onNavigationItemSelected(SETTINGS_MENU_ITEM_ONE, SETTINGS_MENU_ITEM_TWO);

        verifyZeroInteractions(view);
    }

    @Test
    public void isOnBackPressHandled_drawerOpen_callsViewCloseNavigation() throws Exception {
        when(view.isDrawerOpen()).thenReturn(true);

        presenter.isOnBackPressHandled();

        verify(view).closeNavigation();
    }

    @Test
    public void isOnBackPressHandled_drawerOpen_returnsTrue() throws Exception {
        when(view.isDrawerOpen()).thenReturn(true);

        Assert.assertTrue(presenter.isOnBackPressHandled());
    }

    @Test
    public void isOnBackPressHandled_drawerNotOpen_returnsFalse() throws Exception {
        when(view.isDrawerOpen()).thenReturn(false);

        Assert.assertFalse(presenter.isOnBackPressHandled());
    }

    @Test
    public void isOnBackPressHandled_hasNoView_returnsFalse() throws Exception {
        presenter.dropView();

        Assert.assertFalse(presenter.isOnBackPressHandled());
    }
}