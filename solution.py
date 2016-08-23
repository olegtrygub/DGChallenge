import sys

def load_fragments_from_file(path):
    input_file = open(path, 'r')
    curr_fragment = ""
    fragments = set([])
    for line in input_file:
            if line[0] == '>':
                if curr_fragment:
                    fragments.add((curr_fragment, len(curr_fragment) / 2))
                    curr_fragment = ""
            else:
                curr_fragment += line.rstrip()
    if curr_fragment:
        fragments.add((curr_fragment, len(curr_fragment) / 2))
    input_file.close()
    return fragments

def get_sequence(fragments):
    left_fragment, left_merge_point = '', -1

    #get any element from set
    for fragment in fragments:
        if fragment[1] != -1:
            left_fragment, left_merge_point = fragment
            break

    if not left_fragment:
        return fragments.pop()[0]

    mergepart = left_fragment[-1 * left_merge_point:]

    search_fragments = fragments - set([(left_fragment, left_merge_point)])
    for right_fragment, right_merge_point in search_fragments:
        ind = right_fragment.find(mergepart)
        if ind == -1:
            continue

        if left_fragment[-1 * (ind + left_merge_point):-1 * left_merge_point] == right_fragment[:ind]:
            merged_fragment = left_fragment + right_fragment[ind+left_merge_point:], right_merge_point
            seq = get_sequence(search_fragments - set([(right_fragment, right_merge_point)]) | set([merged_fragment]))
            if seq:
                return seq

    return get_sequence(search_fragments | set([(left_fragment, -1)]))

def check_output(fragments, output):
    for fragment in fragments:
        if output.find(fragment[0]) == -1:
            return False
    return True


fragments = load_fragments_from_file(sys.argv[1])
print get_sequence(fragments)
