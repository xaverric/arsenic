package cz.uhk.fim.arsenic.core.service;

public class Services {

    public static final NetworkService NETWORK_SERVICE = new NetworkService();
    public static final AssyncTaskService ASSYNC_TASK_SERVICE = new AssyncTaskService();
    public static final GuiService GUI_SERVICE = new GuiService();
    public static final GeoLocationService GEO_LOCATION_SERVICE = new GeoLocationService();
    public static final NotificationSchedulerService NOTIFICATION_SCHEDULER_SERVICE = new NotificationSchedulerService();
    public static final AlertDialogService ALERT_DIALOG_SERVICE = new AlertDialogService();
}
