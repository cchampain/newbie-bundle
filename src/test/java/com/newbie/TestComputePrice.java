package com.newbie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreInstance;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.automation.OperationContext;
import org.nuxeo.ecm.automation.OperationException;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.platform.test.PlatformFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.PartialDeploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.TargetExtensions;

import junit.framework.Assert;

import javax.inject.Inject;


@RunWith(FeaturesRunner.class)
@Features({ PlatformFeature.class })
// @Deploy({"com.newbie.newbie-bundle-core:OSGI-INF/computeprice-service.xml"})
@PartialDeploy(bundle = "studio.extensions.cchampain-SANDBOX", extensions = { TargetExtensions.ContentModel.class })

public class TestComputePrice {

    @Inject
    protected ComputePrice computeprice;

    @Inject
    protected CoreSession session;

    @Test
    public void testService() {
        assertNotNull(computeprice);
    }

    @Test
    public void testCreateDoc() {

            DocumentModel doc = session.createDocumentModel("/", "myProduct", "product");
            doc = session.createDocument(doc);
            session.save();

            // IdRef docIdRef = new IdRef(doc.getId());
            // doc = session.getDocument(docIdRef);
            // assertNotNull(doc);
            
            // PathRef docPathRef = new PathRef(doc.getPathAsString());
            // doc = session.getDocument(docPathRef);
            // assertNotNull(doc);

            String query = "SELECT * FROM File";
            DocumentModelList queryResults = session.query(query);
            assertEquals(1, queryResults.size());   
        
    }
}
