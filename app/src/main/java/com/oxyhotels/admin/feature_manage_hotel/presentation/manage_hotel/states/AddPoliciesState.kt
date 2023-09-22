package com.oxyhotels.admin.feature_manage_hotel.presentation.manage_hotel.states

data class AddPoliciesState (
    val restrictions:MutableList<String> = mutableListOf(
        "Check-in is allowed any time",
        "No Guaranteed Early check-in",
        "Couples are welcome",
        "Guests can check in using any local or outstation Id Proof (Pan Card not accepted)",
    ),
    val housePoliciesDos:MutableList<String> = mutableListOf(
        "Wear a mask and keep a safe distance of at least 2 meters (6 feet) when interacting with others.",
        "Wash your hands frequently with the soap and running water.",
        "Use the intercom to communicate with property staff for your requirements.",
        "Put all disposable plates/cups/bottles after use in the garbage bag.",
        "In case a balcony is shared with another room, please be on the side of your room. Do not interact with guests of other room.",
        "In case of a medical emergency, contact the reception immediately.",
        "In case of an emergency like fire/earthquake, please gather in the assembly area while maintaining 2 meters (6 feet) distance from others.",
        "Request you to use your own mask and other safety kit, the spare one may not be available at reception."
    ),
    val housePoliciesDonts: MutableList<String>  = mutableListOf(
        "Please do not touch anything outside your room, including sofa or chairs at reception.",
        "Do not use common areas such as reception and minimise interaction with other guests.",
        "Visitors are not allowed in the property, Please use voice/video calls to interact with your dear ones.",
        "Spitting in the common area is strongly prohibited.",
        "Kitchen and washing area entry is prohibited for guests."
    ),
    val houseAmenities:MutableList<String> = mutableListOf(
        "AC",
        "TV",
        "Free Wifi",
        "Power backup",
        "Elevator",
        "In-house Restaurant",
        "CCTV cameras",
        "Private Entrance",
        "Reception",
        "24/7 check-in",
        "Fire Extinguisher",
        "Buzzer/door bell"
    ),
)