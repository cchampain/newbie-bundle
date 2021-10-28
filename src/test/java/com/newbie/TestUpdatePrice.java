package com.newbie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.automation.AutomationService;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.automation.test.AutomationFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.PathRef;

@RunWith(FeaturesRunner.class)
@Features(AutomationFeature.class)
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
@Deploy("com.newbie.newbie-bundle-core")
public class TestUpdatePrice {

    @Inject
    protected CoreSession session;

    @Inject
    protected AutomationService automationService;

    
    @Test
    public void shouldCallTheOperation() throws OperationException {
        OperationContext ctx = new OperationContext(session);
        DocumentModel doc = (DocumentModel) automationService.run(ctx, UpdatePrice.ID);
        assertEquals("/", doc.getPathAsString());
    }

    @Test
    public void shouldCallWithParameters() throws OperationException {
        final String path = "/default-domain";
        OperationContext ctx = new OperationContext(session);
        Map<String, Object> params = new HashMap<>();
        params.put("path", path);
        DocumentModel doc = (DocumentModel) automationService.run(ctx, UpdatePrice.ID, params);
        assertEquals(path, doc.getPathAsString());
    }

    @Test
    public void testCreateDoc() {
            OperationContext ctx = new OperationContext(session);
            DocumentModel doc = session.createDocumentModel("/", "myFile", "File");
            doc = session.createDocument(doc);
            session.save();

            IdRef docIdRef = new IdRef(doc.getId());
            doc = session.getDocument(docIdRef);
            assertNotNull(doc);
            
            PathRef docPathRef = new PathRef(doc.getPathAsString());
            doc = session.getDocument(docPathRef);
            assertNotNull(doc);

            String query = "SELECT * FROM File";
            DocumentModelList queryResults = session.query(query);
            assertEquals(1, queryResults.size());   

    }

}    
