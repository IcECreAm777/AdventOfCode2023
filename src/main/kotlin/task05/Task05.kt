package task05

import Task
import java.io.File


/** SOME EXPLANATIONS OF THE DATA STRUCTURES
 * The conversion maps have the source as key and the destination as value, since multiple sources might end up at
 * the same destination. If a key is not inside the conversion map, the key is translated 1:1
 */


/** Class representing the task of day 5 */
class Task05(inFileName: String, outFileName: String) : Task(inFileName, outFileName) {

    // conversion maps
    private val seedToSoil = mutableListOf<ConversionMap>()
    private val soilToFertilizer = mutableListOf<ConversionMap>()
    private val fertilizerToWater = mutableListOf<ConversionMap>()
    private val waterToLight = mutableListOf<ConversionMap>()
    private val lightToTemperature = mutableListOf<ConversionMap>()
    private val temperatureToHumidity = mutableListOf<ConversionMap>()
    private val humidityToLocation = mutableListOf<ConversionMap>()

    // list of seed ids we use as input
    private val seeds = mutableListOf<SeedRange>()


    override fun initializeTask() {

        // iterate through every line to parse the input
        val reader = File(inputFileName).bufferedReader()
        var line = reader.readLine()
        var listToModify = mutableListOf<ConversionMap>()
        while (line != null) {

            // continue when line is empty
            if(line.isEmpty()) {
                line = reader.readLine()
                continue
            }

            // parsing seeds differs from sub-task to sub-task, so nothing should be done here
            if(line.contains("seeds:")) {
                line = reader.readLine()
                continue
            }

            // switch the list which should be modified right now
            if(line.contains("-to-")) {
                listToModify = switchMapContext(line)
                line = reader.readLine()
                continue
            }

            // every other case should be a line containing a mapping entry, which is being parsed here
            parseConversionMapEntry(line, listToModify)
            line = reader.readLine()
        }

        // close the reader when done
        reader.close()
    }

    override fun generateFirstSubTaskResult(): String {

        // parse seeds based on the first sub-task interpretation
        val reader = File(inputFileName).bufferedReader()
        val line = reader.readLine()
        parseSeedsAsIDs(line)
        reader.close()

        // return the result of traversing the maps as result string
        return traverseMaps().toString()
    }

    override fun generateSecondSubTaskResult(): String {

        // parse seeds based on the second sub-task interpretation
        val reader = File(inputFileName).bufferedReader()
        val line = reader.readLine()
        parseSeedsAsRange(line)
        reader.close()

        // return the result of traversing the maps as result string
        return traverseMaps().toString()
    }


    /** Traverses the conversion maps for every seed to find the smallest location number
     * @return The smallest location ID reachable
     */
    private fun traverseMaps() : Long {
        // the lowest location is the result of this task
        var targetLocation = Long.MAX_VALUE

        // find the location for every seed
        for(seed in seeds) {
            for(i in 0..<seed.range) {
                // generate the seed ID based on the range
                val seedId = seed.start + i

                // traverse the conversion maps
                val soil = findTranslatedValue(seedId, seedToSoil)
                val fertilizer = findTranslatedValue(soil, soilToFertilizer)
                val water = findTranslatedValue(fertilizer, fertilizerToWater)
                val light = findTranslatedValue(water, waterToLight)
                val temperature = findTranslatedValue(light, lightToTemperature)
                val humidity = findTranslatedValue(temperature, temperatureToHumidity)
                val location = findTranslatedValue(humidity, humidityToLocation)

                // update the target location to the smaller one
                targetLocation = if(location < targetLocation) location else targetLocation
            }
        }

        // return the location
        return targetLocation
    }

    /** Finds all the integers in this line and adds them to the seed list
     * @param line The line to parse the seed IDs from
     */
    private fun parseSeedsAsIDs(line: String) {
        val regex = Regex("\\d+")
        for(match in regex.findAll(line)) {
            val seed = match.value.toLong()
            seeds.add(SeedRange(seed, 1)) // range of one since IDs are not treated as range here
        }
    }

    /** Finds all the integers in this line and treats them as start-range pair and adds those to the seed list
     * @param line the line to parse
     */
    private fun parseSeedsAsRange(line: String) {
        // find all the integers
        val regex = Regex("\\d+")
        val matches = regex.findAll(line).toList()

        // iterate through half the matches since 2 values are used at the same time
        for (i in 0..<matches.size / 2) {
            // get the start ID and the range
            val start = matches[2 * i].value.toLong()
            val range = matches[2 * i + 1].value.toLong()

            // add the seed range to the list
            seeds.add(SeedRange(start, range))
        }
    }

    /** Checks the line which things are mapped and returns the map which should be updated
     * @param line The line to parse
     * @return The map which should be modified for the next lines while parsing the input
     */
    private fun switchMapContext(line: String) : MutableList<ConversionMap> {
        if(line.contains("seed") && line.contains("soil"))
            return seedToSoil
        if(line.contains("soil") && line.contains("fertilizer"))
            return soilToFertilizer
        if(line.contains("fertilizer") && line.contains("water"))
            return fertilizerToWater
        if(line.contains("water") && line.contains("light"))
            return waterToLight
        if(line.contains("light") && line.contains("temperature"))
            return lightToTemperature
        if(line.contains("temperature") && line.contains("humidity"))
            return temperatureToHumidity
        if(line.contains("humidity") && line.contains("location"))
            return humidityToLocation

        return mutableListOf()
    }

    /** Parses a conversion map entry by translating the start and range to entries and adding them to the map
     * @param line The line being parsed
     * @param list The map the parsed entries will be added to
     */
    private fun parseConversionMapEntry(line: String, list: MutableList<ConversionMap>) {

        // read the values from the line
        val entries = line.split(' ')
        val destinationStart = entries[0].toLong()
        val sourceStart = entries[1].toLong()
        val range = entries[2].toLong()

        // add the map to the list
        list.add(ConversionMap(sourceStart, destinationStart, range))
    }

    /** Tries to find the source value inside the conversion maps and returns the translated value
     * @param source The source to look for
     * @param list The list of conversion maps to look into
     * @return The translated value if a map is found or the source value when no conversion is needed
     */
    private fun findTranslatedValue(source: Long, list: MutableList<ConversionMap>) : Long {

        // find a map which has the incoming source
        for(map in list) {
            if(map.isSourceInRange(source)) return map.translateSourceToDestination(source)
        }

        // return the source if no map was found
        return source
    }
}