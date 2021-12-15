/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author 2dam
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(restful.CommentFacadeREST.class);
        resources.add(restful.DwellingFacadeREST.class);
        resources.add(restful.FacilityFacadeREST.class);
        resources.add(restful.FlatFacadeREST.class);
        resources.add(restful.FlatFacilityFacadeREST.class);
        resources.add(restful.GuestFacadeREST.class);
        resources.add(restful.LastSignInFacadeREST.class);
        resources.add(restful.NeighbourhoodFacadeREST.class);
        resources.add(restful.OwnerFacadeREST.class);
        resources.add(restful.RoomFacadeREST.class);
        resources.add(restful.ServiceFacadeREST.class);
        resources.add(restful.UserFacadeREST.class);
    }

}