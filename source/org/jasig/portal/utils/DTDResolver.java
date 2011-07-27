/* Copyright 2001 The JA-SIG Collaborative.  All rights reserved.
*  See license distributed with this file and
*  available online at http://www.uportal.org/license.html
*/

package org.jasig.portal.utils;

import java.io.InputStream;

import org.jasig.portal.PortalSessionManager;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * Provides a means to resolve uPortal DTDs
 * @author Peter Kharchenko, pkharchenko@unicon.net
 * @author Ken Weiner, kweiner@unicon.net
 * @author Dave Wallace, dwallace@udel.edu modifications 
 * @version $Revision$
 */
public class DTDResolver implements EntityResolver
{
  private String dtdPath = "dtd";
  private String dtdName = null;

  /**
   * Constructor for DTDResolver
   */
  public DTDResolver () {
  }

  /**
   * Constructor for DTDResolver
   * @param dtdName the name of the dtd
   */
  public DTDResolver (String dtdName) {
    this.dtdName = dtdName;
  }

  /**
   * Sets up a new input source based on the dtd specified in the xml document
   * @param publicId the public ID
   * @param systemId the system ID
   * @return an input source based on the dtd specified in the xml document
   *               or null if we don't have a dtd that matches systemId or publicId
   */
  public InputSource resolveEntity (String publicId, String systemId) {
    InputStream inStream = null;

    // Check for a match on the systemId
    if (systemId != null) {
      if (dtdName != null && systemId.indexOf(dtdName) != -1)
        inStream = PortalSessionManager.getResourceAsStream(dtdPath + "/" + dtdName);
      else if (systemId.trim().equalsIgnoreCase("http://my.netscape.com/publish/formats/rss-0.91.dtd"))
        inStream = PortalSessionManager.getResourceAsStream(dtdPath + "/rss-0.91.dtd");
         
      if ( null != inStream )
          return new InputSource(inStream);
    }
    
    // Check for a match on the public id
    if ( publicId != null ) {
        if ( publicId.trim().equalsIgnoreCase("-//Netscape Communications//DTD RSS 0.91//EN"))
            inStream = PortalSessionManager.getResourceAsStream(dtdPath + "/rss-0.91.dtd");
            
        if ( null != inStream )
            return new InputSource(inStream);
    }
        
    // Return null to let the parser handle this entity 
    return null;
  }
}