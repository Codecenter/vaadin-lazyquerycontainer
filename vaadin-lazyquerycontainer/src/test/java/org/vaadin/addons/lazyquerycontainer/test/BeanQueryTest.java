package org.vaadin.addons.lazyquerycontainer.test;

import com.vaadin.data.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.vaadin.addons.lazyquerycontainer.AbstractBeanQuery;
import org.vaadin.addons.lazyquerycontainer.BeanQueryFactory;
import org.vaadin.addons.lazyquerycontainer.LazyQueryDefinition;
import org.vaadin.addons.lazyquerycontainer.LazyQueryView;
import org.vaadin.addons.lazyquerycontainer.QueryItemStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * The test class for BeanQueryFactory.
 */
public class BeanQueryTest {

    @Before
    public final void setUp() throws Exception {
        MockBeanQuery.reset();
    }

    @Test
    public final void testLoadItems() {
        LazyQueryDefinition queryDefinition = new LazyQueryDefinition(true, 50);
        queryDefinition.addProperty(LazyQueryView.PROPERTY_ID_ITEM_STATUS, QueryItemStatus.class, QueryItemStatus.None,
                true, false);
        queryDefinition.addProperty("name", String.class, "test-bean-2", true, false);

        Map<String, Object> queryConfiguration = new HashMap<String, Object>();
        queryConfiguration.put("description", "test-bean-description-2");

        BeanQueryFactory<MockBeanQuery> factory = new BeanQueryFactory<MockBeanQuery>(MockBeanQuery.class);
        factory.setQueryConfiguration(queryConfiguration);
        factory.setQueryDefinition(queryDefinition);
        LazyQueryView view = new LazyQueryView(queryDefinition, factory);

        Assert.assertEquals(queryDefinition, view.getQueryDefinition());
        Assert.assertEquals(1, view.size());
        Item item = view.getItem(0);
        Assert.assertEquals("test-bean-1", item.getItemProperty("name").getValue());
        Assert.assertEquals("test-bean-description-1", item.getItemProperty("description").getValue());
    }

    @Test
    public void testSaveItems() {
        BeanQueryFactory<MockBeanQuery> factory = new BeanQueryFactory<MockBeanQuery>(MockBeanQuery.class);
        LazyQueryView view = new LazyQueryView(factory, true, 50);
        LazyQueryDefinition queryDefinition = (LazyQueryDefinition) view.getQueryDefinition();
        queryDefinition.addProperty(LazyQueryView.PROPERTY_ID_ITEM_STATUS, QueryItemStatus.class, QueryItemStatus.None,
                true, false);
        queryDefinition.addProperty("name", String.class, "test-bean-2", true, false);

        Map<String, Object> queryConfiguration = new HashMap<String, Object>();
        queryConfiguration.put("description", "test-bean-description-2");

        factory.setQueryConfiguration(queryConfiguration);
        factory.setQueryDefinition(queryDefinition);

        Assert.assertEquals(1, view.size());
        int index = view.addItem();
        Assert.assertEquals(2, view.size());
        Item item = view.getItem(index);
        Assert.assertEquals("test-bean-2", item.getItemProperty("name").getValue());
        Assert.assertEquals("test-bean-description-2", item.getItemProperty("description").getValue());
        Assert.assertFalse((Boolean) item.getItemProperty("saved").getValue());
        view.commit();
        Assert.assertTrue((Boolean) item.getItemProperty("saved").getValue());
    }

    @Test
    public void testSaveItemsWithoutCompositeItems() {
        BeanQueryFactory<MockBeanQuery> factory = new BeanQueryFactory<MockBeanQuery>(MockBeanQuery.class);
        LazyQueryView view = new LazyQueryView(factory, false, 50);
        LazyQueryDefinition queryDefinition = (LazyQueryDefinition) view.getQueryDefinition();
        queryDefinition.addProperty("name", String.class, "test-bean-2", true, false);

        Map<String, Object> queryConfiguration = new HashMap<String, Object>();
        queryConfiguration.put("description", "test-bean-description-2");

        factory.setQueryConfiguration(queryConfiguration);
        factory.setQueryDefinition(queryDefinition);

        Assert.assertEquals(1, view.size());
        int index = view.addItem();
        Assert.assertEquals(2, view.size());
        Item item = view.getItem(index);
        Assert.assertEquals("test-bean-2", item.getItemProperty("name").getValue());
        Assert.assertEquals("test-bean-description-2", item.getItemProperty("description").getValue());
        Assert.assertFalse((Boolean) item.getItemProperty("saved").getValue());
        view.commit();
        Assert.assertTrue((Boolean) item.getItemProperty("saved").getValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveAllItems() {
        LazyQueryDefinition queryDefinition = new LazyQueryDefinition(true, 50);
        queryDefinition.addProperty(LazyQueryView.PROPERTY_ID_ITEM_STATUS, QueryItemStatus.class, QueryItemStatus.None,
                true, false);
        queryDefinition.addProperty("name", String.class, "test-bean-2", true, false);

        Map<String, Object> queryConfiguration = new HashMap<String, Object>();
        queryConfiguration.put("description", "test-bean-description-2");

        BeanQueryFactory<MockBeanQuery> factory = new BeanQueryFactory<MockBeanQuery>(MockBeanQuery.class);
        factory.setQueryConfiguration(queryConfiguration);
        factory.setQueryDefinition(queryDefinition);
        LazyQueryView view = new LazyQueryView(queryDefinition, factory);

        view.removeAllItems();
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidConstruction() {
        BeanQueryFactory testFactory = new BeanQueryFactory<AbstractBeanQuery>(AbstractBeanQuery.class);
        testFactory.constructQuery(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void testFailingItemConstruction() {
        LazyQueryDefinition queryDefinition = new LazyQueryDefinition(true, 50);
        queryDefinition.addProperty(LazyQueryView.PROPERTY_ID_ITEM_STATUS, QueryItemStatus.class, QueryItemStatus.None,
                true, false);
        queryDefinition.addProperty("name", String.class, "test-bean-2", true, false);

        Map<String, Object> queryConfiguration = new HashMap<String, Object>();
        queryConfiguration.put("description", "test-bean-description-2");

        BeanQueryFactory<MockBeanQueryWithFailingItemConstruction> factory = new BeanQueryFactory<MockBeanQueryWithFailingItemConstruction>(MockBeanQueryWithFailingItemConstruction.class);
        factory.setQueryConfiguration(queryConfiguration);
        factory.setQueryDefinition(queryDefinition);
        LazyQueryView view = new LazyQueryView(queryDefinition, factory);

        view.addItem();
    }

    @Test
    public void testAbstractBeanQueryDefaultConstructor() {
        new MockBeanQuery();
    }
}
